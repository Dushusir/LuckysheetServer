package com.xc.luckysheet.controller;


import com.mongodb.DBObject;
import com.xc.common.utils.JsonUtil;
import com.xc.luckysheet.entity.TuGridModel;
import com.xc.luckysheet.entity.enummodel.OperationTypeEnum;
import com.xc.luckysheet.postgre.server.PostgresGridFileGetService;
import com.xc.luckysheet.utils.MyUtil;
import com.xc.luckysheet.utils.Pako_GzipUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *  图表格页面调用
 *  目前权限，控制为修改 表格所有者
 *  1、读取、查看  a、未登录 必须该工作簿设置为分享  b、已登录  （必须为自己的工作簿 或者 该工作簿已分享）
 *  2、修改，必须为本人所有的表格
 * @author Administrator
 */
@Slf4j
@RestController
@Api(description = "luckysheet测试接口")
@RequestMapping(value = {"/tu/api","/datatu/tu/api"})
public class JfGridFileController {
    /*
        /默认页面  test_gridkey  测试mongodb的key在

        /load       加载默认表格   （）
        /loadsheet  加载指定表格   （）


         已删除代码

        /update     更新       (使用共享编辑)
        /updateImg  更新图片

        以下代码注释
        /loadsheetpart  加载指定表格部分数据  （仅mongo实现）
        *未处理
        /saveas      整个表格另存为  ()
                     整个表格另存为 sign (这个另存为是将页面的对象另存为新表格，未与数据库结合)

        打开表格
        /tu/api?id=listid
    */

    /**
     * 是否启用pgsql的开关
     */
    public static String pgSetUp="";

    @Autowired
    private PostgresGridFileGetService pgGridFileGetService;

    /**
     * 默认加载表格 分块
     * @param request
     * @param response
     * @param gridKey
     * @return
     */
    @ApiOperation(value = "默认加载表格",notes = "默认加载表格")
    //@RequestMapping(value={"/load"}, produces = "text/html;charset=utf-8")
    //@ResponseBody
    @PostMapping("/load")
    public String load(HttpServletRequest request, HttpServletResponse response, @RequestParam(defaultValue = "") String gridKey) {
        //告诉浏览器，当前发送的是gzip格式的内容
        response.setHeader("Content-Encoding", "gzip");
        response.setContentType("text/html");
        String resultStr="";
        if(gridKey.trim().length()!=0){
            try {
                //ObjectId _ObjectId= JfGridFileUtil.getObjectId(gridKey);
                String _checkStr=check(request,gridKey.toString(),null,OperationTypeEnum.Read);
                if(_checkStr.length()>0){
                    return null;
                }
                List<DBObject> dbObject=null;
                if("0".equals(pgSetUp)){
                    //postgre
                    dbObject=pgGridFileGetService.getDefaultByGridKey(gridKey);
                }else{
                    //mongo
                    //dbObject=jfGridFileGetService.getDefaultByGridKey(gridKey);
                }
                if(dbObject!=null){
                    resultStr=JsonUtil.toJson(dbObject);
                }
            } catch (Exception e) {
                log.error(gridKey+e.getMessage());
            }


        }
        log.info("load");
        try {
            byte dest[]= Pako_GzipUtils.compress2(resultStr);
            OutputStream out=response.getOutputStream();
            out.write(dest);
            out.close();
            out.flush();
        } catch (Exception e) {
            log.error("load---ioerror:"+e);
        }
        return null;
    }

    /**
     * 加载指定表格 分块
     * @param map
     * @param request
     * @param response
     * @param gridKey
     * @param index
     * @return
     */
    @ApiOperation(value = "加载指定表格",notes = "加载指定表格")
//    @RequestMapping(value={"/loadsheet"}, produces = "text/html;charset=utf-8")
//    @ResponseBody
    @PostMapping("/loadsheet")
    public byte[] loadsheet(Map map, HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(defaultValue = "") String gridKey,
                            @RequestParam(defaultValue = "") String[] index) {
        log.info("loadsheet--gridKey:"+gridKey+" index:"+ Arrays.toString(index));
        ////告诉浏览器，当前发送的是gzip格式的内容
        response.setHeader("Content-Encoding", "gzip");
        response.setContentType("text/html");
        String resultStr="";
        if(gridKey.trim().length()!=0){
            try {
                String _id=gridKey;
                String _checkStr=check(request,_id,null,OperationTypeEnum.Read);
                log.info(_checkStr);
                if(_checkStr.length()>0){
                    return null;
                }
                LinkedHashMap dbObject=null;
                if("0".equals(pgSetUp)){
                    //postgre
                    dbObject=pgGridFileGetService.getByGridKeys(_id, Arrays.asList(index));
                }else{
                    //mongo
                    //dbObject=jfGridFileGetService.getByGridKeys(_id, Arrays.asList(index));
                }
                log.info("loadsheet--dbObject--");
                if(dbObject!=null){
                    resultStr=JsonUtil.toJson(dbObject);
                }
            } catch (Exception e) {
                log.info(gridKey+e.getMessage());
            }
        }

        byte dest[]= Pako_GzipUtils.compress2(resultStr);
        log.info("loadsheet");
        OutputStream out=null;
        try {
            out = response.getOutputStream();
            out.write(dest);
            out.close();
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("loadsheet---ioerror:"+e);
        }catch (Exception ex){
            log.error("loadsheet---error:"+ex);
        }

        return null;
    }

//    /**
//     * 加载指定表格部分数据，不做分块处理，
//     * 测试方法在  http://localhost:863/test 中
//     * @param map
//     * @param request
//     * @param response
//     * @param model
//     * @return
//     */
//    @RequestMapping(value={"/loadsheetpart"}, produces = "text/html;charset=utf-8")
//    @ResponseBody
//    public byte[] loadsheetpart(Map map, HttpServletRequest request,HttpServletResponse response,
//                                @ModelAttribute("")SheetDataSearchModel model) {
//        log.info(JsonUtil.toJson(model));
//        //告诉浏览器，当前发送的是gzip格式的内容
//        response.setHeader("Content-Encoding", "gzip");
//        response.setContentType("text/html");
//
//        String resultStr="";
//        try {
//            if(model.check()){
//
//                String _checkStr=check(request,model.getList_id().toString(),null,OperationTypeEnum.Read);
//                log.info(_checkStr);
//                if(_checkStr.length()>0){
//                    return null;
//                }
//                List<DBObject> dbObject=jfGridFileGetService.getSheetPartDataByGridKey(model);
//                if(dbObject!=null){
//                    resultStr=JsonUtil.toJsonKeepNull(dbObject);
//                }
//            }
//        } catch (Exception e) {
//            log.error(model.getGridKey()+e.getMessage());
//        }
//
//        byte dest[]= Pako_GzipUtils.compress2(resultStr);
//        return dest;
//    }

//    //整个表格另存为 sign (这个另存为是将页面的对象另存为新表格，与数据库结合)
//    //目前为用户根目录下
//    @RequestMapping(value={"/saveas"}, produces = "text/html;charset=utf-8")
//    @ResponseBody
//    public String saveas(Map map, HttpServletRequest request,
//                         @RequestParam(defaultValue = "false")boolean compress,
//                         @RequestParam(defaultValue = "") String tablename,
//                         @RequestParam(defaultValue = "") String data,
//                         @RequestParam(defaultValue = "") String sign
//    ) {
//        logger.info("compress:"+compress);
//        logger.info("tablename:"+tablename);
//        logger.info("data:"+data);
//        logger.info("sign:"+sign);//压缩后的MD5
//
//
//        RequestUpDateModel m=new RequestUpDateModel();
//        m.setStatus(false);
//        m.setContent("另存为操作失败");
//
//        UserModel userModel= SessionModelUtil.getLoginModel(request);
//        if(userModel==null){
//            m.setContent("未登录用户");
//            return new Gson().toJson(m);
//        }
//       /* //权限判断
//        String checkAuth=userModel.checkAuth(AuthorityEnum.AddTable);
//        if(checkAuth.length()>0){
//            m.setContent(checkAuth);
//            return new Gson().toJson(m);
//        }
//        //限制判断
//        String _checkStr=authorityService.checkTableDirOper(userModel, UserOperEnum.AddTable,null);
//        logger.info(_checkStr);
//        if(_checkStr.length()>0){
//            m.setContent(_checkStr);
//            return new Gson().toJson(m);
//        }*/
//
//        if(MD5Util.MD5Encode(data, "UTF-8").equals(sign) ||  MD5Util.MD5Encode(MyURLUtil.urlEncode(data),"UTF-8").equals(sign)){
//
//        }else{
//            m.setContent("sign错误");
//            return new Gson().toJson(m);
//        }
//
//        //获取实际数据
//        String str="";
//        if(compress){
//            //需要解压
//            str= Pako_GzipUtils.unCompressToURI(data);
//        }else{
//            str= MyURLUtil.urlDecode(data);
//        }
//        //logger.info("data:"+str);
//        DBObject dbObject=(DBObject) JSON.parse(str);
//        if(dbObject!=null){
//            List<DBObject> _dbObject=null;
//            if(dbObject instanceof List){
//                _dbObject=(List)dbObject;
//            }else{
//                m.setContent("数据错误");
//                return new Gson().toJson(m);
//            }
//
//
//            //检查信息
//            TuGridModel model=new TuGridModel();
//            model.setGrid_name(tablename.trim());//文件名
//            model.setParent_list_id("0");//直接保存到用户根目录下
//            String _result=model.checkAddFile(true);
//            if(_result.length()>0){
//                m.setContent(_result);
//                return new Gson().toJson(m);
//            }
//            if(!tuGridService.dirExistsByListId(model.getParent_list_id())){
//                m.setContent("文件夹不存在");
//                return new Gson().toJson(m);
//            }
//            //获取用户在当前层级中是否存在相同名字的文件夹
//            model.setGrid_creator(userModel.getUser_id());
//            TuGridModel _searchModel=tuGridService.selectByUnique(model);
//            if(_searchModel!=null){
//                m.setContent(model.getGrid_name()+" 已存在相同文件名");
//                return new Gson().toJson(m);
//            }
//            model.setList_id(CustomIdentifierGenerator.id());
//            try{
//                int _i=tuGridService.insertTuGrid(model,_dbObject);
//                if(_i>0){
//                    //返回操作成功的数据
//                    m.setContent(model.getList_id().toString());
//                    m.setStatus(true);
//                }else{
//                    //m.setContent("操作失败");
//                }
//            }catch (Exception ex){
//                logger.error("saveas--exception:"+ex);
//            }
//
////            String _id=jfGridFileUpdataService.insert(_dbObject);
////            if(_id!=null){
////                m.setStatus(true);
////                m.setContent(_id);
////            }
//        }
//        return new Gson().toJson(m);
//    }


    /**
     * 修改、读取ajax 如果_ObjectId为空，使用id
     * @param request
     * @param _id
     * @param curmodel
     * @param operationTypeEnum
     * @return
     */
    private String check(HttpServletRequest request, String _id, TuGridModel curmodel, OperationTypeEnum operationTypeEnum){
        //测试,用于演示
        if(MyUtil.isShow){
            return "";
        }
        return "";
    }
}

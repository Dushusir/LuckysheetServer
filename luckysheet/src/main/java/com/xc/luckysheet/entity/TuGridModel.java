package com.xc.luckysheet.entity;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.xc.luckysheet.entity.enummodel.GridTypeEnum;
import com.xc.luckysheet.utils.MyFileUtil;
import com.xc.luckysheet.utils.MyListUtil;
import com.xc.luckysheet.utils.MyStringUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 1
 * Date: 17-12-15
 * Time: 上午11:34
 * To change this template use File | Settings | File Templates.
 */
@Data
public class TuGridModel implements BaseModel{
    private String list_id;
    private String parent_list_id;// 上一级id ，等级为0  ,-1 回收站

    private String grid_name;// '表格名称',
    private String grid_introduction;// '表格说明',
    private byte[] grid_thumb;// '缩略图',
    private Long grid_creator;// '创建人id',
    private GridTypeEnum grid_type;// '表格类型 表格类型 ;dir 0;tu、1;xls、2;csv、3;xlsx、4;txt、5;

    private String mongodbkey;// 'mongodbkey',
    private Integer grid_statistic_sheet;// 'sheet数量',
    private String grid_filepath;// '源文件路径',（此处为 用户id/记录id.文件类型）  路径为 配置/用户id/记录id.文件类型

    private Date grid_create_time;// '创建时间',
    private Date grid_update_time;// '最后修改时间',
    private Integer grid_status;// '状态 1正常 -1垃圾箱',
    private String grid_datashow_key;// '数据秀发布key',
    private Integer grid_datashow_public;// '是否发布数据秀 0否 1是',
    private Integer grid_share_status;// '是否分享 0否 1是',
    private Integer allow_share_status;//是否允许分享  1允许(默认) 0不允许
    private Long category_id;//分类id
    private String grid_tag;// '标签tag', ","分割

    private Integer grid_statistic_favorite;// '收藏次数',
    private Integer grid_statistic_comment;// '评论次数',
    private Integer grid_statistic_like;// '点赞次数',
    private Long grid_statistic_view;// '浏览次数',

    private Long uploadsize;//上传文件的大小 字节
    private Long mongodbsize;//mongodb的大小 字节



    //不存储
    private List<TuGridModel> tuGridModels=new ArrayList<TuGridModel>();//下级文件
    private UserModel userModel; //用户信息
    private MultipartFile uploadFile;//上传的文件
    private String grid_thumb_base64;//图像
    private List<String> tags;//标签（上传文件时使用）
    private String category_path;//分类路径 (执行存储过程获取)
    //搜索使用
    private Date start_grid_create_time;// '创建时间',
    private Date end_grid_create_time;// '创建时间',
    private Date start_grid_update_time;// '最后修改时间',
    private Date end_grid_update_time;// '最后修改时间',

    private Long dirsize;//文件夹大小
    private String uploadsizeStr;//上传文件的大小
    private String mongodbsizeStr;//mongodb的大小
    private String dirsizeStr;//文件夹大小

    public String checkAddDir(){

        if(StringUtils.isBlank(this.grid_name)){
            return "文件夹"+errorNotBlank;
        }
        this.grid_name= MyStringUtil.replaceBlank(this.grid_name.trim());
        if(this.grid_name.length()>100){
            return "文件夹名称不能大于100个字符";
        }

        if(this.parent_list_id==null && "0".equals(this.parent_list_id)){
            return "上一级文件夹"+errorNotBlank;
        }

        return "";
    }
    public String checkReNameDir(){
        if(this.list_id==null ){
            return "参数错误";
        }
        if(StringUtils.isBlank(this.grid_name)){
            return "文件夹"+errorNotBlank;
        }
        this.grid_name=this.grid_name.trim();

        return "";
    }
    public String checkDelDir(){
        if(this.list_id==null){
            return "参数错误";
        }
        return "";
    }
    public String checkRecoveryDir(){
        if(this.list_id==null ){
            return "参数错误";
        }
        if(this.grid_name!=null){
            this.grid_name=this.grid_name.trim();
        }
        return "";
    }
    public String checkShare(){
        if(this.list_id==null ){
            return "参数错误";
        }
        if(this.category_id==null || this.category_id==0){
            return "参数错误";
        }
        return "";
    }

    /**
     * 添加文件检查  isCreateTuGrid 是否创建兔表格
     * @param isCreateTuGrid
     * @return
     */
    public String checkAddFile(boolean isCreateTuGrid){
        if(StringUtils.isBlank(this.grid_name)){
            return "文件名"+errorNotBlank;
        }
        //this.grid_name=this.grid_name.trim();
        this.grid_name= MyStringUtil.replaceBlank(this.grid_name.trim());
        if(this.grid_name.length()>100){
            return "文件名称不能大于100个字符";
        }

        //if(this.parent_list_id==null && this.parent_list_id==0){
        if(this.parent_list_id==null){
            return "所在文件夹"+errorNotBlank;
        }

        if(this.grid_share_status==null){
            this.grid_share_status=0;
        }
        if(this.grid_share_status==null || (this.grid_share_status!=0 && this.grid_share_status!=1)){
            return "是否分享"+errorNotSet;
        }
        if(this.category_id==null){
            this.category_id=0L;
        }
        if(this.grid_share_status==1 && this.category_id.equals(0l)){
            return "分类"+errorNotSet;
        }

        if(isCreateTuGrid){
            //创建兔表格
            this.grid_type=GridTypeEnum.tu;
        }else{
            //上传文件
            if(this.uploadFile.isEmpty()){
                return "上传文件"+errorNotBlank;
            }
            //记录上传文件大小
            this.uploadsize=this.uploadFile.getSize();

            String extName= MyFileUtil.getExtensionName(this.uploadFile);
            GridTypeEnum gridTypeEnum=GridTypeEnum.getUploadFileEnum(extName);
            if(gridTypeEnum==null){
                return "上传文件扩展名不支持";
            }
            this.grid_type=gridTypeEnum;
        }


        if(this.grid_introduction!=null){
            this.grid_introduction=this.grid_introduction.trim();
        }

        //标签处理
        if(this.getGrid_tag()!=null){
            List<String> _tags=MyListUtil.getListByStr(this.getGrid_tag(),",");
            if(_tags!=null || _tags.size()>0){
                this.setTags(_tags);
            }
        }

        return "";
    }

    /**
     * 获取空的兔表格
     * @return
     */
    public static DBObject getTuGridEmpty(){
        //整个
        String model="{\"jfgridfile\":[{\"name\":\"Sheet1\",\"color\":\"\",\"index\":0,\"chart\":[],\"status\":1,\"order\":0,\"column\":60,\"row\":84,\"celldata\":[],\"visibledatarow\":[],\"visibledatacolumn\":[],\"rowsplit\":[],\"ch_width\":4748,\"rh_height\":1790,\"jfgird_select_save\":null,\"jfgrid_selection_range\":[],\"scrollLeft\":0,\"scrollTop\":0,\"config\":{}},{\"name\":\"Sheet2\",\"color\":\"\",\"index\":1,\"chart\":[],\"status\":0,\"order\":1,\"column\":60,\"row\":84,\"celldata\":[],\"visibledatarow\":[],\"visibledatacolumn\":[],\"rowsplit\":[],\"ch_width\":4748,\"rh_height\":1790,\"jfgird_select_save\":{},\"jfgrid_selection_range\":{},\"scrollLeft\":0,\"scrollTop\":0,\"config\":{}},{\"name\":\"Sheet3\",\"color\":\"\",\"index\":2,\"chart\":[],\"status\":0,\"order\":2,\"column\":60,\"row\":84,\"celldata\":[],\"visibledatarow\":[],\"visibledatacolumn\":[],\"rowsplit\":[],\"ch_width\":4748,\"rh_height\":1790,\"jfgird_select_save\":{},\"jfgrid_selection_range\":{},\"scrollLeft\":0,\"scrollTop\":0,\"config\":{}}]}";
        DBObject bson=(DBObject) JSON.parse(model);
        return bson;
    }

    /**
     * 获取单个sheet结构  setRange 是否设置范围（row col）
     * @return
     */
    public static DBObject getTuGideSheet(){
        return getTuGideSheet(false);
    }
    public static DBObject getTuGideSheet(boolean setRange){
        String model="{\"name\":\"Sheet1\",\"color\":\"\",\"index\":0,\"chart\":[],\"status\":1,\"order\":0,\"column\":60,\"row\":84,\"celldata\":[],\"visibledatarow\":[],\"visibledatacolumn\":[],\"rowsplit\":[],\"ch_width\":4748,\"rh_height\":1790,\"jfgird_select_save\":null,\"jfgrid_selection_range\":[],\"scrollLeft\":0,\"scrollTop\":0,\"config\":{},\"is_delete\":0}";
        DBObject bson=(DBObject) JSON.parse(model);
        bson.put("block_id",JfGridConfigModel.FirstBlockID);
        //设置默认块
        //固定大小
//        if(setRange) {
//            //设置块的大小
//            bson.put("rsize", JfGridConfigModel.getRow_size());
//            bson.put("csize", JfGridConfigModel.getCol_size());
//        }
        return bson;
    }
    
    public static DBObject getPgGideSheet(boolean setRange){
        String model="{\"name\":\"Sheet1\",\"color\":\"\",\"chart\":[],\"column\":60,\"row\":84,\"celldata\":[],\"visibledatarow\":[],\"visibledatacolumn\":[],\"rowsplit\":[],\"ch_width\":4748,\"rh_height\":1790,\"jfgird_select_save\":null,\"jfgrid_selection_range\":[],\"scrollLeft\":0,\"scrollTop\":0,\"config\":{}}";
        DBObject bson=(DBObject) JSON.parse(model);
        //设置默认块
        //固定大小
//        if(setRange) {
//            //设置块的大小
//            bson.put("rsize", JfGridConfigModel.getRow_size());
//            bson.put("csize", JfGridConfigModel.getCol_size());
//        }
        return bson;
    }

    /**
     * txt、csv得到的数据转换
     * @param _csvData
     * @return
     */
    public static DBObject arrayToCelldata(List<String[]> _csvData){
        BasicDBList _resultModel=new BasicDBList();
        if(_csvData!=null){
            for(int r=0;r<_csvData.size();r++){
                String[] cs=_csvData.get(r);
                if(cs!=null && cs.length>0){
                    for(int c=0;c<cs.length;c++){
                        if(StringUtils.isNotBlank(cs[c])){
                            DBObject _v=new BasicDBObject();
                            _v.put("v",cs[c].trim());
                            DBObject _bson=new BasicDBObject();
                            _bson.put("r",r);
                            _bson.put("c",c);
                            _bson.put("v",_v);
                            _resultModel.add(_bson);
                        }
                    }
                }
            }
        }

        //默认3个工作簿
        int sheetcount=3;
        List<DBObject> _list=new ArrayList<DBObject>();
        for(int x=0;x<sheetcount;x++){
            DBObject _sheet1=TuGridModel.getTuGideSheet(true);
            //第一个工作簿，设置值
            if(_list.size()==0){
                _sheet1.put("status",1);
                _sheet1.put("celldata",_resultModel);
            }else{
                _sheet1.put("status",0);
            }
            _sheet1.put("name","Sheet"+(_list.size()+1));
            _sheet1.put("index",_list.size());
            _sheet1.put("order",_list.size());

            if(1==2){
                //做分组处理
                _list.addAll(JfGridConfigModel.toDataSplit(_sheet1));
            }else{
                //是否压缩数据
                //GzipHandle.toCompressBySheet(_sheet1);
                Long _size=0L;
                if(_sheet1.toString().length()>16777216){
                    _size=16777216L;
                }else{
                    _size=MyFileUtil.getSizeByStr(_sheet1.toString());
                }

                if(_size>=15728640){
                    //大于15M清空
                    _sheet1.put("celldata",new BasicDBList());
                    _list.add(_sheet1);
                }else{
                    _list.add(_sheet1);
                }
            }

        }

//        DBObject _sheet1=getTuGideSheet();
//        DBObject _sheet2=getTuGideSheet();
//        DBObject _sheet3=getTuGideSheet();
//        //第一个工作簿，设置值
//        _sheet1.put("celldata",_resultModel);
//
//        List<DBObject> _list=new ArrayList<DBObject>();
//        _list.add(_sheet1);
//        _list.add(_sheet1);
//        _list.add(_sheet1);

        DBObject _b=new BasicDBObject();
        _b.put("jfgridfile",_list);
        return _b;
    }
    public static DBObject getTuGridEmpty(String model){
        try{
            DBObject dbObject=(DBObject) JSON.parse(model);
            if(dbObject!=null && dbObject.containsField("jfgridfile")){
                Object bson=dbObject.get("jfgridfile");
                if(bson instanceof BasicDBList || bson instanceof List){
                    List<DBObject> _list=(List<DBObject>)bson;
                    for(int x=0;x<_list.size();x++){
                        //是否压缩
                       // GzipHandle.toCompressBySheet(_list.get(x));
                    }
                }
                return dbObject;
            }
            return null;
        }catch (Exception ex){
            return null;
        }
    }

    //搜索检查
    public void searchCheck(){
        if(this.grid_name!=null){
            this.grid_name=this.grid_name.trim();
        }
        if(this.category_id!=null && this.category_id<=0){
            this.category_id=null;
        }
        if(this.grid_share_status!=null && this.grid_share_status<0){
            this.category_id=null;
        }
        if(this.grid_creator!=null && this.grid_creator<=0){
            this.grid_creator=null;
        }
        if(this.grid_status!=null && this.grid_status.equals(0)){
            this.grid_status=null;
        }

        if(this.grid_status!=null && this.grid_status.equals(-2)){
            this.grid_status=null;
            this.allow_share_status=0;
        }

        this.setTuGridModels(null);
    }

    //大小处理
    public static void sizeImghandle(List<TuGridModel> _models){
        if(_models!=null && _models.size()>0){
            for(TuGridModel tuGridModel:_models){
                sizeImghandle(tuGridModel);
            }
        }
    }
    public static void sizeImghandle(TuGridModel _model){
        if(_model!=null){
            if(_model.getUploadsize()!=null){
                _model.setUploadsizeStr(MyFileUtil.FormetFileSize(_model.getUploadsize()));
            }
            if(_model.getMongodbsize()!=null){
                 _model.setMongodbsizeStr(MyFileUtil.FormetFileSize(_model.getMongodbsize()));
            }
            if(_model.getDirsize()!=null && _model.getDirsize()>0){
                _model.setDirsizeStr(MyFileUtil.FormetFileSize(_model.getDirsize()));
            }
            if(_model.getGrid_thumb()!=null){
                //_m.setGrid_thumb_base64(Base64Util.byte2Base64StringFun(_m.getGrid_thumb()));
                //_model.setGrid_thumb_base64("data:image/jpg;base64,"+new String(_model.getGrid_thumb()));
                _model.setGrid_thumb_base64(new String(_model.getGrid_thumb()));
                _model.setGrid_thumb(null);
            }
        }
    }


}

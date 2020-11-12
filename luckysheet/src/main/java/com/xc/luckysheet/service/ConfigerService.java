package com.xc.luckysheet.service;

import com.xc.luckysheet.controller.JfGridFileController;
import com.xc.luckysheet.entity.JfGridConfigModel;
import com.xc.luckysheet.redisserver.RedisMessagePublish;
import com.xc.luckysheet.websocket.MyWebSocketHandler;
import com.xc.luckysheet.websocket.WebSocketConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created with IntelliJ IDEA.
 * User: 3
 * Date: 17-6-12
 * Time: 上午10:47
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Configuration
@Service
public class ConfigerService {
    /**
     * false 代码中测试部分不启用
     */
    public static boolean isTest=true;

    public final static String gridkey="5a52d4faff46dbf93039cba9";

    public final static String[] AGENT = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" };


    //上传文件路径
    public static String uploadPath = null;
    //ralis 路径
    public static String railsUrl=null;
    //xls处理aspose授权文件路径
    public static String asposePath=null;
    //全部地址前缀
    public static String urlHead=null;
    //数据库类型
    public static String dbType="1";

    @Value("${rails.url}")
    public void setRailsUrl(String path){
        railsUrl=path;
    }
    @Value("${upload.path}")
    public void setUploadPath(String path){
        uploadPath=path;
    }
    @Value("${aspose.path}")
    public void setAsposePath(String path){
        asposePath=path;
    }
    @Value("${urlHead}")
    public void setUrlHead(String path){
        urlHead=path;
    }
    @Value("${dbType}")
    public void setDbType(String path){
        dbType=path;
    }

    //其他配置

    @Value("${redis.channel}")
    public void setRedisChannel(String path){
        RedisMessagePublish.channel=path;
    }
    @Value("${row_size}")
    public void setRowSize(Integer rowSize){
        JfGridConfigModel.row_size=rowSize;
    }
    @Value("${col_size}")
    public void setColSize(Integer colSize){
        JfGridConfigModel.col_size=colSize;
    }
    @Value("${pgSetUp}")
    public void setPgSetUp(String pgSetUp){
        JfGridFileController.pgSetUp=pgSetUp;
        MyWebSocketHandler.pgSetUp=pgSetUp;
    }
    @Value("${servertype}")
    public void setServerType(String servertype){
        WebSocketConfig.servertype=servertype;
    }

    public Map getCommonJspConfing(HttpServletRequest request, HttpServletResponse response) {

        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragrma","no-cache");
        response.setDateHeader("Expires",0);

        Map<String, Object> backMap = new HashMap<String, Object>();
        //判断电脑OR手机
        backMap.put("ismobile",checkIsMobile(request));
        backMap.put("test_gridkey",gridkey);
        backMap.put("urlHead",urlHead);

        return backMap;
    }

    /**
     * 判断浏览器是否支持GZIP
     * @param request
     * @return
     */
    public boolean isGZipEncoding(HttpServletRequest request){
        boolean flag=false;
        String encoding=request.getHeader("Accept-Encoding");
        if(encoding!=null && encoding.indexOf("gzip")!=-1){
            flag=true;
        }
        return flag;
    }

    /**
     * @param map
     * @param total  结果总数
     * @param pageNo 当前页码
     */
    public void handlerPage(Map map, int total, int pageNo,int pageCount,String params) {
        int pagetotal = (total + pageCount - 1) / pageCount;

        map.put("total", total);
        map.put("pageNo", pageNo);
        map.put("pageCount", pagetotal);
        if (pageNo <= pagetotal) {
            if(map.containsKey("isMobile") && map.get("isMobile").toString().equals("1")){
                map.put("htmlstartpage", Math.max(1, pageNo - 5-5));
                map.put("htmlendpage", Math.min(pagetotal, pageNo + 6-5));
            }else{
                map.put("htmlstartpage", Math.max(1, pageNo - 5));
                map.put("htmlendpage", Math.min(pagetotal, pageNo + 6));
            }
        }

        if (params != null && params.trim().length() > 0) {
            map.put("params", params);
        }
    }

    /**
     * true 手机  false 电脑
     * @param request
     * @return
     */
    public static boolean checkIsMobile(HttpServletRequest request) {
        String ua=request.getHeader("User-Agent");
        boolean flag = false;
        if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
            // 排除 苹果桌面系统
            if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
                for (String item : AGENT) {
                    if (ua.contains(item)) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

}

package com.xc.luckysheet.websocket;

import com.xc.luckysheet.utils.MyURLUtil;
import com.xc.luckysheet.utils.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Socket建立连接（握手）和断开
 * @author Administrator
 */
@Slf4j
public class MyWebSocketInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        //解决The extension [x-webkit-deflate-frame] is not supported问题
//        if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
//            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
//        }

        //websocket系统启动连接程序，启动的时候就会把他的session值传过来，放入到websocketsession（websocket的一个内置服务器）里面
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        HttpSession session=servletRequest.getServletRequest().getSession(false);
        log.info("beforeHandshake--session:"+session);
        //用户token
        String token =getParam(servletRequest,WSUserModel.USER_TOKEN);
        //文档id
        String gridKey = getParam(servletRequest,WSUserModel.USER_GRIDKEY);
        if (null!=token && null!=gridKey) {
        	//验证用户信息
        	String gridKeys=MyURLUtil.urlDecode(gridKey);
        	log.info("gridKey:"+gridKeys);
        	
        	String _checkStr=check(gridKeys);
             log.info(_checkStr);
             if(_checkStr.length()>0){
                 return false;
             }
            attributes.put(WSUserModel.USER_TOKEN, token);
            attributes.put(WSUserModel.USER_GRIDKEY, gridKey);
        } else {
            return false;
        }
        return true;
    }


    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {
    	log.info("exception"+exception);
    }


    private String getParam(ServletServerHttpRequest servletRequest, String key){
        String value=servletRequest.getServletRequest().getParameter(key);
        if(value!=null){
            value=value.trim();
            if(value.length()>0){
                return  value;
            }
        }
        return null;
    }

    /**
     * /修改、读取ajax 如果_ObjectId为空，使用id
     * @param _id
     * @return
     */
    private String check(String _id){
        //测试,用于演示
        if(MyUtil.isShow){
            return "";
        }
//        //获取对应的记录
//        //按mongodbkey获取 创建人id(grid_creator) 表格id(list_id)，表格状态（grid_status 1正常 -1垃圾箱 ）是否分享 (grid_share_status 0否 1是)
//        //_model=tuGridService.selectGridCreatorByMongodbkey(_ObjectId.toString());
//        TuGridModel _model=tuGridService.selectGridCreatorByListId(_id);
//
//        if(_model==null){
//            return "表格不存在";
//        }
//        if(!_model.getGrid_status().equals(1)){
//            return "表格已删除";
//        }

        return "";
    }
}

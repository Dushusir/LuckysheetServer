package com.xc.luckysheet.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: 1
 * Date: 15-7-2
 * Time: 下午4:42
 * To change this template use File | Settings | File Templates.
 */
public class RequestUrl {


    public static  String sendGet(String url) {
        return sendGet(url,0,0);
    }
    public static  String sendGet(String url,int connectTimeout,int readTimeout) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            //urlNameString="http://169.254.4.6:8080/solr";
            //urlNameString="http://169.254.4.6:8080/solr/name2/select?q=12334" ;
            //System.out.println("send:"+urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            if(connectTimeout>0){
                connection.setConnectTimeout(connectTimeout);
            }
            if(readTimeout>0){
                connection.setReadTimeout(readTimeout);
            }
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            //connection.setRequestProperty("accept", "application/json,text/javascript,*/*");
            //connection.setRequestProperty("accept-language", "zh-Hans-CN,zh-Hans");
            //connection.setRequestProperty("content-type", "text/xml;charser=utf-8");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
//            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            //in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
//            System.out.println(DateUtil.parseTime(new Date())+"发送sendGet请求出现异常:" + e+" URL:"+url);
            //e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    public static  String sendGet(String url, String param) {
        return sendGet(url,param,0,0);
    }
    //connectTimeout 连接时长毫秒
    //readTimeout    读取时长毫秒
    public static  String sendGet(String url, String param,int connectTimeout,int readTimeout) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            //urlNameString="http://169.254.4.6:8080/solr";
            //urlNameString="http://169.254.4.6:8080/solr/name2/select?q=12334" ;
            //System.out.println("send:"+urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            if(connectTimeout>0){
                connection.setConnectTimeout(connectTimeout);
            }
            if(readTimeout>0){
                connection.setReadTimeout(readTimeout);
            }
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            //connection.setRequestProperty("accept", "application/json,text/javascript,*/*");
            //connection.setRequestProperty("accept-language", "zh-Hans-CN,zh-Hans");
            //connection.setRequestProperty("content-type", "text/xml;charser=utf-8");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
//            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            //in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
//            System.out.println(DateUtil.parseTime(new Date())+"发送sendGet请求出现异常:" + e+" URL:"+url+"?"+param);
            //e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static  String sendPost2(String url, String param) {
        return sendPost2(url,param,0,0);
    }
    public static  String sendPost2(String url, String jsonparam,int connectTimeout,int readTimeout) {

        BufferedReader in = null;
        String result = "";
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection  connection=(HttpURLConnection)realUrl.openConnection();
            if(connectTimeout>0){
                connection.setConnectTimeout(connectTimeout);
            }
            if(readTimeout>0){
                connection.setReadTimeout(readTimeout);
            }
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");

            OutputStream out=connection.getOutputStream();
            out.write(jsonparam.getBytes("UTF-8"));  //
            out.flush();

            // 定义 BufferedReader输入流来读取URL的响应
            //in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
//            System.out.println(DateUtil.parseTime(new Date())+"发送sendPost2请求出现异常:" + e+" URL:"+url+"?"+jsonparam);
            //e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static  String sendPost(String url, String param) {
        return sendPost(url,param,0,0);
    }
    public static  String sendPost(String url, String param,int connectTimeout,int readTimeout) {

        PrintWriter out=null;
        BufferedReader in = null;
        String result = "";
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection  connection=(HttpURLConnection)realUrl.openConnection();
            if(connectTimeout>0){
                connection.setConnectTimeout(connectTimeout);
            }
            if(readTimeout>0){
                connection.setReadTimeout(readTimeout);
            }
            //URLConnection connection = realUrl.openConnection();
            connection.setRequestMethod("POST");
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Charset","UTF-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);
//            connection.connect();
            //获取Post请求输出流

            out=new PrintWriter(connection.getOutputStream());
            out.print(param);

            out.flush();

            // 定义 BufferedReader输入流来读取URL的响应
            //in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
//            System.out.println(DateUtil.parseTime(new Date())+"发送sendPost请求出现异常:" + e+" URL:"+url+"?"+param);
            //e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args){
        String compress="false";
        String gridKey="5a4b23daff462a7d85c27f2c";
        String data="%7B%22t%22%3A%22c%22%2C%22i%22%3A0%2C%22v%22%3A%22%7B%5C%22sheetIndex%5C%22%3A0%2C%5C%22dataSheetIndex%5C%22%3A0%2C%5C%22option%5C%22%3Anull%2C%5C%22chartType%5C%22%3A%5C%22line%5C%22%2C%5C%22selfOption%5C%22%3A%5C%22%7B%7D%5C%22%2C%5C%22defaultOption%5C%22%3A%5C%22%7B%5C%5C%5C%22title%5C%5C%5C%22%3A%7B%5C%5C%5C%22text%5C%5C%5C%22%3A%5C%5C%5C%22215400%5C%5C%5C%22%2C%5C%5C%5C%22subtext%5C%5C%5C%22%3A%5C%5C%5C%22%5C%5C%5C%22%7D%2C%5C%5C%5C%22animationDuration%5C%5C%5C%22%3A500%2C%5C%5C%5C%22tooltip%5C%5C%5C%22%3A%7B%5C%5C%5C%22trigger%5C%5C%5C%22%3A%5C%5C%5C%22axis%5C%5C%5C%22%7D%2C%5C%5C%5C%22legend%5C%5C%5C%22%3A%7B%5C%5C%5C%22data%5C%5C%5C%22%3A%5B%5C%5C%5C%22210400%5C%5C%5C%22%5D%7D%2C%5C%5C%5C%22toolbox%5C%5C%5C%22%3A%7B%5C%5C%5C%22feature%5C%5C%5C%22%3A%7B%5C%5C%5C%22saveAsImage%5C%5C%5C%22%3A%7B%7D%7D%7D%2C%5C%5C%5C%22xAxis%5C%5C%5C%22%3A%7B%5C%5C%5C%22type%5C%5C%5C%22%3A%5C%5C%5C%22category%5C%5C%5C%22%2C%5C%5C%5C%22data%5C%5C%5C%22%3A%5B%5C%5C%5C%2294750%5C%5C%5C%22%2C%5C%5C%5C%22260912%5C%5C%5C%22%2C%5C%5C%5C%22121.1%25%5C%5C%5C%22%5D%7D%2C%5C%5C%5C%22yAxis%5C%5C%5C%22%3A%7B%5C%5C%5C%22type%5C%5C%5C%22%3A%5C%5C%5C%22value%5C%5C%5C%22%2C%5C%5C%5C%22max%5C%5C%5C%22%3A376487%2C%5C%5C%5C%22jfgrid_formatratio%5C%5C%5C%22%3A10000%2C%5C%5C%5C%22jfgrid_suffix%5C%5C%5C%22%3A%5C%5C%5C%22%E4%B8%87%5C%5C%5C%22%2C%5C%5C%5C%22jfgrid_floatlen%5C%5C%5C%22%3A0%2C%5C%5C%5C%22axisLabel%5C%5C%5C%22%3A%7B%7D%7D%2C%5C%5C%5C%22series%5C%5C%5C%22%3A%5B%7B%5C%5C%5C%22name%5C%5C%5C%22%3A%5C%5C%5C%22210400%5C%5C%5C%22%2C%5C%5C%5C%22type%5C%5C%5C%22%3A%5C%5C%5C%22line%5C%5C%5C%22%2C%5C%5C%5C%22data%5C%5C%5C%22%3A%5B100575%2C206108%2C0.98%5D%2C%5C%5C%5C%22smooth%5C%5C%5C%22%3Afalse%2C%5C%5C%5C%22jfgrid_formatratio%5C%5C%5C%22%3A10000%2C%5C%5C%5C%22jfgrid_suffix%5C%5C%5C%22%3A%5C%5C%5C%22%E4%B8%87%5C%5C%5C%22%2C%5C%5C%5C%22jfgrid_floatlen%5C%5C%5C%22%3A1%2C%5C%5C%5C%22label%5C%5C%5C%22%3A%7B%5C%5C%5C%22normal%5C%5C%5C%22%3A%7B%7D%7D%7D%5D%7D%5C%22%2C%5C%22row%5C%22%3A%5C%22%5B1%2C2%5D%5C%22%2C%5C%22column%5C%22%3A%5C%22%5B2%2C5%5D%5C%22%2C%5C%22chart_id%5C%22%3A%5C%22jfgrid-datav-chart-1%5C%22%2C%5C%22chart_selection_color%5C%22%3A%5C%22%2327727b%5C%22%2C%5C%22chart_selection_id%5C%22%3A%5C%22jfgrid-datav-chart-1_selection%5C%22%2C%5C%22chartStyle%5C%22%3A%5C%22default%5C%22%2C%5C%22rangeConfigCheck%5C%22%3Atrue%2C%5C%22rangeRowCheck%5C%22%3Atrue%2C%5C%22rangeColCheck%5C%22%3Atrue%2C%5C%22chartMarkConfig%5C%22%3A%5C%22%7B%7D%5C%22%2C%5C%22chartTitleConfig%5C%22%3A%5C%22%7B%7D%5C%22%2C%5C%22winWidth%5C%22%3A1245%2C%5C%22winHeight%5C%22%3A714%2C%5C%22scrollLeft1%5C%22%3A0%2C%5C%22scrollTop1%5C%22%3A0%2C%5C%22chartTheme%5C%22%3A%5C%22default0000%5C%22%2C%5C%22myWidth%5C%22%3A498%2C%5C%22myHeight%5C%22%3A307.764%2C%5C%22myLeft%5C%22%3A322.5%2C%5C%22myTop%5C%22%3A132%2C%5C%22myindexrank%5C%22%3A100%7D%22%2C%22cid%22%3A%22jfgrid-datav-chart-1%22%2C%22op%22%3A%22add%22%7D";
        //http://localhost:862/tu/api/test/update
        String result=sendPost("http://192.168.1.3:3002","gridKey="+gridKey+"&data="+data);
        System.out.println(result);
    }
    public static  String sendPostParaJson(String url, String param) {
        return sendPostParaJson(url,param,0,0);
    }
    public static  String sendPostParaJson(String url, String param,int connectTimeout,int readTimeout) {

        PrintWriter out=null;
        BufferedReader in = null;
        String result = "";
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection=(HttpURLConnection)realUrl.openConnection();
            if(connectTimeout>0){
                connection.setConnectTimeout(connectTimeout);
            }
            if(readTimeout>0){
                connection.setReadTimeout(readTimeout);
            }
            //URLConnection connection = realUrl.openConnection();
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            // 设置通用的请求属性
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Charset","UTF-8");

            connection.setDoInput(true);
            connection.setDoOutput(true);
//            connection.connect();
            //获取Post请求输出流
            out=new PrintWriter(connection.getOutputStream());
            out.print(param);
            out.flush();

            // 定义 BufferedReader输入流来读取URL的响应
            //in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

        } catch (Exception e) {
//            System.out.println(DateUtil.parseTime(new Date())+"发送sendPostParaJson请求出现异常:" + e+" URL:"+url+"?"+param);
            //e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String string2Unicode(String str){
        String result="";
        for(int i=0;i<str.length();i++){
            int char1=(char)str.charAt(i);
            if(char1>=19968 && char1<=171941){
                result+="\\u"+ Integer.toHexString(char1);
            }else{
                result+=str.charAt(i);
            }
        }
        return result;
    }
    public static String unicode2String(String unicode){
        StringBuilder sb=new StringBuilder();
        int i=-1;
        int pos=0;
        while((i=unicode.indexOf("\\u",pos))!=-1){
            sb.append(unicode.substring(pos,i));
            if(i+5<unicode.length()){
                pos=i+6;
                sb.append((char)Integer.parseInt(unicode.substring(i+2,i+6),16));
            }
        }
        sb.append(unicode.substring(pos));
        return sb.toString();
    }


    public static String getIpAddress(HttpServletRequest request){
        //这个是nginx赋值的，直接请求时值为空
        String ip ="";
        try{
            ip = request.getHeader("X-Real-IP");
            if (StringUtils.isBlank(ip)) {
                //ip = request.getRemoteAddr();
                ip=getIpAddress2(request);
            }
        }catch (Exception ex){
        }
        return ip;
    }
    private static String getIpAddress2(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    public static String getRequestParam(HttpServletRequest request){
        StringBuilder _sb=new StringBuilder();
        if(request!=null){
            Enumeration enumeration=request.getParameterNames();
            while(enumeration.hasMoreElements()){
                String _pName=(String)enumeration.nextElement();
                _sb.append(_pName+"["+request.getParameter(_pName)+"]");
            }
        }
        return _sb.toString();
    }
}

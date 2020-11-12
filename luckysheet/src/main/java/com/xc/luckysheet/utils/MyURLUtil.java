package com.xc.luckysheet.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: 1
 * Date: 17-11-16
 * Time: 上午9:38
 * To change this template use File | Settings | File Templates.
 */
public class MyURLUtil {
    //解码
    public static String urlDecode(String str){
        try {
            String strReturn= URLDecoder.decode(str, "UTF-8");
            return strReturn;
        } catch (UnsupportedEncodingException e) {
           System.out.println("urlDecode error:"+str+" info:"+e.toString());
        }
        return null;
    }
    //编码
    public static String urlEncode(String str){
        try {
            String strReturn= URLEncoder.encode(str, "UTF-8");
            return strReturn;
        } catch (UnsupportedEncodingException e) {
            System.out.println("urlEncode error:"+str+" info:"+e.toString());
        }
        return null;
    }

    //字符串转字节
    public static byte[] stringTobyte(String str){
        return stringTobyte(str,"ISO-8859-1");
    }
    public static byte[] stringTobyte(String str,String charsetName){
        try {
            return str.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            System.out.println("stringTobyte error:"+str+" info:"+e.toString());
        }
        return null;
    }


    public static void main(String[] args){
        String str="%5B%7B%22t%22%3A%22v%22%2C%22i%22%3A1%2C%22v%22%3A%22%E8%BF%90%E8%BE%93%E8%AE%BE%E5%A4%87%22%2C%22r%22%3A1%2C%22c%22%3A2%7D%5D";
        System.out.println(urlDecode(str));
        String str1="[{\"t\":\"v\",\"i\":1,\"v\":\"运输设备\",\"r\":1,\"c\":2}]";
        System.out.println(urlEncode(str1));

        byte[] bytes1=stringTobyte("123测试abc","UTF-8");
        byte[] bytes=stringTobyte("123测试abc");
        System.out.println(bytes.toString());
    }
}

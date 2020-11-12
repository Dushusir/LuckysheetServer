package com.xc.luckysheet.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class MyUtil {
    //显示版本（最终要删除掉）
    public static boolean isShow=false;

    public static String getUUId(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
    public static String getUUId2(){
        return getUUId().replace("-", "");
    }

    public  static boolean isMobile(String mobile) {
        if(mobile==null || mobile.length()!=11){
            return false;
        }

        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(mobile);
        b = m.matches();
        return b;
    }

    public static String getMobileGoodsSign(String mobile,String gid,String type){
        Map<String,String> _map=new HashMap<String,String>();
        _map.put("mobile",mobile);
        _map.put("gid",gid);
        _map.put("type",type);
        _map=paraFilter(_map);
        return MD5Util.MD5Encode(getSortString(_map),"UTF-8");
    }
    /**
     * 除去数组中的空值
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("")){
                continue;
            }
            result.put(key, value);
        }
        return result;
    }
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private static String getSortString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    static String regEx_AllNumber="^[\\-|\\+]?\\d+(\\.\\d+)?$";
    public static boolean isAllNumber(String str){
        Pattern p= Pattern.compile(regEx_AllNumber);
        Matcher m=p.matcher(str);
        if(m.find()){
            return  true;
        }
        return false;
    }
    static String regEx_AllCN="^[\\u4e00-\\u9fa5]+$";
    public static boolean isAllCN(String str){
        Pattern p= Pattern.compile(regEx_AllCN);
        Matcher m=p.matcher(str);
        if(m.find()){
            return  true;
        }
        return false;
    }
    static String regEx_AllEN="^[a-zA-Z]+$";
    public static boolean isAllEN(String str){
        Pattern p= Pattern.compile(regEx_AllEN);
        Matcher m=p.matcher(str);
        if(m.find()){
            return  true;
        }
        return false;
    }
    static String regEx_isHaveEN="[a-zA-Z]+";
    public static boolean isHaveEN(String str){
        Pattern p= Pattern.compile(regEx_isHaveEN);
        Matcher m=p.matcher(str);
        if(m.find()){
            return  true;
        }
        return false;
    }

    //null非数字
    public static Integer getStringToInt(String str){
        if(isInt(str)){
            try{
                return Integer.parseInt(str);
            }catch (Exception ex){
                System.out.println(ex.toString());
                return null;
            }
        }
        return null;
    }
    public static boolean isInt(String str) {
        if(str.equals("0")){
            return true;
        }
        //采用正则表达式的方式来判断一个字符串是否为数字，这种方式判断面比较全
        //可以判断正负、整数小数
        boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
        return isInt;
    }
    public static boolean isNumber(String str) {
        //采用正则表达式的方式来判断一个字符串是否为数字，这种方式判断面比较全
        //可以判断正负、整数小数
        boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
        boolean isDouble = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$").matcher(str).find();
        return isInt || isDouble;
    }


}

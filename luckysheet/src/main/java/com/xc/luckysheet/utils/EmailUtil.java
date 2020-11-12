package com.xc.luckysheet.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class EmailUtil {

    private static Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    public static  void  main(String[] args){
        System.out.println(isEmail("12@qq.com"));
        System.out.println(isEmail("mk@qq.com.au"));
        System.out.println(isEmail("12@qq.com"));
    }
    public static boolean isEmail(String email){
        if (null==email || "".equals(email))
            return false;
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static void SendEmail(String email,String content){
        System.out.println(email+" => "+content);
    }


}

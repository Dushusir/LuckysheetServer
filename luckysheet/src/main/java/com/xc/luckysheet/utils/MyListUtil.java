package com.xc.luckysheet.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Administrator
 */
public class MyListUtil<T> {

    /**
     * 示例： (Test_0, Test_1, Test_2, Test_3, Test_4, Test_5, Test_6, Test_7, Test_8, Test_9)
     * @param list
     * @return
     */
    public static String ArrayListToString(List<?> list){
        if(list==null || list.size()==0){
            return "";
        }else{
            return  Arrays.toString(list.toArray()).replace('[','(').replace(']', ')');
        }
    }

    /**
     * 示例('Test_0','Test_1','Test_2','Test_3','Test_4','Test_5','Test_6','Test_7','Test_8','Test_9')
     * @param list
     * @return
     */
    public static String ArrayListToString2(List<?> list){
        if(list==null || list.size()==0){
            return "";
        }else{
            return  "('"+StringUtils.join(list,"','")+"')";
        }
    }

    /**
     * 示例 Test_0, Test_1, Test_2, Test_3, Test_4, Test_5, Test_6, Test_7, Test_8, Test_9
     * @param list
     * @return
     */
    public static String ArrayListToString3(List<?> list){
        if(list==null || list.size()==0){
            return "";
        }else{
            return  Arrays.toString(list.toArray()).replace('[',' ').replace(']', ' ');
        }
    }

    public static List<String> getListByStr(String str,String symbol){
        List<String> _s=new ArrayList<String>();
        if(str!=null && symbol!=null && symbol.length()>0){
            String[] ss=str.trim().split(symbol);
            for(String s:ss){
                if(s.length()>0 && !_s.contains(s)){
                    _s.add(s);
                }
            }
        }
        return _s;

    }

    public static void main(String[] args){

        String symobl="管理员、测试、";
        List<String>_list3=getListByStr(symobl,"、");

        String teststr="newAdd(=*=)13[=*=]Add-1(=*=)14[=*=]xcxcxcx(=*=)27";
        List<String> _list1= MyListUtil.getListByStr(teststr,"\\[\\=\\*\\=\\]");

        List<String> _list=new ArrayList<String>();
        for(int x=0;x<10;x++){
            _list.add("Test_"+x);
        }
        //System.out.println(StringUtil.join(_list,"','"));
        System.out.println(ArrayListToString(_list));
        System.out.println(ArrayListToString2(_list));
        System.out.println(ArrayListToString3(_list));

        String _str="12,12,454,454545,";
        System.out.println(_str+" => "+ArrayListToString(getListByStr(_str,",")));
        _str=",12,454,454545,";
        System.out.println(_str+" => "+ArrayListToString(getListByStr(_str,",")));
        _str="12,454,454545";
        System.out.println(_str+" => "+ArrayListToString(getListByStr(_str,",")));
    }
}

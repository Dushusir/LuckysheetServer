package com.xc.luckysheet.entity.enummodel;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表格类型
 */
@Slf4j
public enum GridTypeEnum {

    dir("dir",0),
    tu("tu",1),
    xls("xls",2),
    csv("csv",3),
    xlsx("xlsx",4),
    txt("txt",5);

    private String value;
    private Integer i;

    public static List<String> list=new ArrayList<String>();
    static Map<Integer,GridTypeEnum> enumMap=new HashMap<Integer, GridTypeEnum>();
    static Map<String,GridTypeEnum> enumMapStr=new HashMap<String, GridTypeEnum>();
    static Map<String,GridTypeEnum> enumMapUploadFileStr=new HashMap<String, GridTypeEnum>();
    static{
        for(GridTypeEnum type:GridTypeEnum.values()){
            enumMap.put(type.getI(), type);
            enumMapStr.put(type.getValue(), type);

            if(GridTypeEnum.dir!=type){
                list.add(type.getValue());
            }
            if(GridTypeEnum.dir==type){

            }else{
                enumMapUploadFileStr.put(type.getValue(), type);
            }
        }
    }

   

    public static GridTypeEnum getEnum(Integer value) {
        if(value==null || !enumMap.containsKey(value)){
            return null;
        }
        return enumMap.get(value);
    }
    public static GridTypeEnum getEnum(String value) {
        if(value==null || !enumMapStr.containsKey(value)){
            return null;
        }
        return enumMapStr.get(value);
    }

    public static GridTypeEnum getUploadFileEnum(String value) {
        if(value==null || !enumMapUploadFileStr.containsKey(value)){
            return null;
        }
        return enumMapUploadFileStr.get(value);
    }


    private GridTypeEnum(String value, Integer i) {
        this.value=value;
        this.i=i;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }
}

package com.xc.luckysheet.entity.enummodel;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 1
 * Date: 18-1-24
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 */
public enum AuthorityEnum {
    AddTable("新建表格",1),
    ShareTable("分享表格",2),
    ShowTable("数据秀",3),
    UnlimitedTable("表格数量无限制",4),

    ManageTable("管理表格",5),
    ManageUser("管理用户",6),
    ReleaseAnnouncement("发布公告",7),
    WebsiteSetting("网站设置",8),
    //管理员
    superAuthority("superAuthority",10);


    private String value;
    private int i;
    AuthorityEnum(String str, Integer i){
        this.value=str;
        this.i=i;
    }
    public static AuthorityEnum get(String value){
        for(AuthorityEnum p: AuthorityEnum.values()){
            if(p.getValue().equals(value)){
                return p;
            }
        }
        return null;
    }
    public static AuthorityEnum get(Integer i){
        for(AuthorityEnum p: AuthorityEnum.values()){
            if(p.getI()==i){
                return p;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public int getI() {
        return i;
    }
}

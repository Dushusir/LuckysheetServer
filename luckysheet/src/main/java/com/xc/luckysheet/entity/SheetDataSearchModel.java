package com.xc.luckysheet.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

/**
 * 表格数据搜索类
 */
@Data
public class SheetDataSearchModel {
    /**
     * 表格的id 数据库id
     */
    private Long list_id;
    /**
     * 表格的key（界面传入，就是数据库id）
     */
    private String gridKey;
    private ObjectId objectId;

    /**
     * sheet序号
     */
    private String index;
    /**
     * 开始行号
     */
    private Integer rowst;
    /**
     * 结束行号
     */
    private Integer rowed;
    /**
     * 开始列号
     */
    private Integer colst;
    /**
     * 结束列号
     */
    private Integer coled;

    public boolean check() throws Exception{
        if(StringUtils.isEmpty(this.getGridKey())){
            return false;
        }

        //this.setObjectId(JfGridFileUtil.getObjectId(this.getGridKey()));
        Long _id=Long.parseLong(this.getGridKey());
        this.list_id=_id;
        if(this.list_id==0){
            return false;
        }

        if(this.index==null){
            return false;
        }
        if(this.rowst==null){
            return false;
        }
        if(this.rowed==null){
            return false;
        }
        if(this.colst==null){
            return false;
        }
        if(this.coled==null){
            return false;
        }
        return true;
    }

}

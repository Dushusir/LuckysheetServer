package com.xc.luckysheet.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 字典
 * @author Administrator
 */
@Data
public class DicModel extends BaseOperationModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /**
     * 字典编号
     */
	private Integer dic_id;
    /**
     * 字典名称
     */
    private String dic_name;
    /**
     * 大类id
     */
    private Integer class_id;

    //不存储
    /**
     * 用于界面添加 大类的同时添加 字典
     */
    private String class_name;

    public String check(){
        if(StringUtils.isBlank(this.dic_name)){
            return "字典名称"+errorNotBlank;
        }
        this.dic_name=this.dic_name.trim();


        if(this.getDic_id().equals(0)){
            //添加时检查
            if(this.class_id!=null && this.class_id>0){
                this.class_name=null;
            }else{
                if(StringUtils.isBlank(this.class_name)){
                    return "大类"+errorNotSet;
                }
                this.class_name=this.class_name.trim();
                this.class_id=null;
            }
        }
        return "";
    }


}

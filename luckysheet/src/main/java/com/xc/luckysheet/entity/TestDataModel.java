package com.xc.luckysheet.entity;

import com.mongodb.DBObject;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: xc
 * Date: 17-5-29
 * Time: 下午3:01
 * To change this template use File | Settings | File Templates.
 */
@Data
public class TestDataModel {
	Integer id;//success error
    String list_id;//内容
    Integer index;//内容

    Integer status;//总数
    String block_id;//每页数量
    DBObject json_data;//总页数
    Integer order;
    

}

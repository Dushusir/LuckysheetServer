package com.xc.luckysheet.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: xc
 * Date: 17-5-29
 * Time: 下午3:01
 * To change this template use File | Settings | File Templates.
 */
@Data
public class ResultModel {
    String result;//success error
    String content;//内容
    String content2;//内容

    Integer total;//总数
    Integer eachcount;//每页数量
    Integer pagetotal;//总页数
    Integer pageNo;//当前页码


}

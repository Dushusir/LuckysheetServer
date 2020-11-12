package com.xc.luckysheet.entity;

import lombok.Data;

/**
 *
 * @author Administrator
 */
@Data
public class AuthorityModel {
    private Long list_id;
    /**
     * 权限名称
     */
    private String authority_name;
    /**
     * 权限编号
     */
    private Integer authority_id;
    /**
     * 权限说明
     */
    private String authority_remark;

    //不存储

    /**
     * 状态 1有权限，其它无权限
     */
    private Integer authority_status;

}

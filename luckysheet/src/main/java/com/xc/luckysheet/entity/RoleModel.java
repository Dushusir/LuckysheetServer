package com.xc.luckysheet.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 角色
 * @author Administrator
 */
public class RoleModel extends BaseOperationModel {
    /**
     * 角色id
     */
    private Integer role_id;
    /**
     * 角色名
     */
    private String role_name;
    /**
     * 角色描述
     */
    private String role_remark;

    private String list_id;
    
    

    public String getList_id() {
		return list_id;
	}

	public void setList_id(String list_id) {
		this.list_id = list_id;
	}

	//不存储

    /**
     * 具体权限
     */
    private List<AuthorityModel> authorityModels;

    public String check(){
        if(StringUtils.isBlank(this.role_name)){
            return "角色名"+errorNotBlank;
        }
        this.role_name=this.role_name.trim();

        return "";
    }

    public List<AuthorityModel> getAuthorityModels() {
        return authorityModels;
    }

    public void setAuthorityModels(List<AuthorityModel> authorityModels) {
        this.authorityModels = authorityModels;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRole_remark() {
        return role_remark;
    }

    public void setRole_remark(String role_remark) {
        this.role_remark = role_remark;
    }
}

package com.xc.luckysheet.entity;



import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户对象
 * @author Administrator
 */
@Data
public class UserModel implements BaseModel,Serializable {
    private Long user_id;//'用户id',
    private String user_email;//'邮箱',
    private String user_password;//'密码',
    private Integer user_activate;//'0 未激活 1激活',
    private String user_phone;//'手机号',
    private Integer user_phone_valid;//'手机号验证 1通过',
    private byte[] user_avatar;//'用户头像,
    private String user_nickname;//'昵称',(如果不存在，使用第三方登录的昵称)

    /*
    *   新浪微博登陆
    *   user_socialapp_type=weibo
    *   user_socialapp_name=screen_name 用户昵称
    *   user_socialapp_id=id  用户uid
    * */
    private String user_socialapp_type;//'绑定类型', weibo、qq、wecha
    private String user_socialapp_name;//'绑定昵称',
    private String user_socialapp_id;//'绑定ID',

    private Integer user_nation;//'国籍',
    private Integer user_province;//'地区',
    private Integer user_city;//'城市',
    private Integer user_career_state;//'0学生 1在职 ',
    private String user_career_school;//'学校',
    private String user_career_college;//'专业',
    private String user_career_company;//'公司',
    private String user_career_industry;//'行业',
    private String user_career_position;//'职务',
    private String user_introduction;//'个人简介',
    private Date user_register_time;//'注册时间',
    private Date user_last_login_time;//最后登录时间

    private Long user_mem_point;//'积分',
    private Integer user_mem_grade;//'等级',
    private String user_config_order;//'排序设置',
    private String user_config_showstate;//'显示设置',
    private Integer user_statistics_share;//'分享数量',
    private Long user_group_id;//'圈子ID',
    private Integer user_statistics_follow;//'关注数量',
    private Integer user_statistics_favorite;//'收藏数量',
    private Long user_like_id;//'兴趣id',
    private Integer state;//'状态 1正常 2冻结 -1删除',

    private String uuid;//uuid验证邮箱的正确性

    //系统帐号 无法删除  0普通用户 1超级管理员  2其它管理
    //1超级管理员 全部权限，0、2用户都与权限相关
    private Integer system_account;


    private Long capacity_size;//容量大小，0无限制 单位M  ()


}

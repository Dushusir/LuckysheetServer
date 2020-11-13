package com.xc.luckysheet.entity;


import com.xc.luckysheet.entity.enummodel.AuthorityEnum;
import com.xc.luckysheet.service.ConfigerService;
import com.xc.luckysheet.utils.EmailUtil;
import com.xc.luckysheet.utils.MD5Util;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    //不存储
    private MultipartFile user_avatar_file;//'用户头像,
    private String user_avatar_base64;//图像
    private List<Integer> authIdList;//用户对应的角色权限id
    private List<RoleModel> roleModels;//用户对应的角色

    private Integer role_id;//搜索用户角色
    private Date createStartTime;//搜索创建时间
    private Date createEndTime;//搜索创建时间
    private Date loginStartTime;//搜索最后登录时间
    private Date loginEndTime;//搜索最后登录时间

    private Integer user_statistics_table;//'表格数量',查询时直接获取
    private Integer user_statistics_folder;//'文件夹数量', 查询时直接获取
    private Long user_statistics_table_size;//大小    查询时直接获取
    private String user_statistics_table_size_str;//大小字符串

    private DicModel dicNation;//'国籍',
    private DicModel dicProvince;//'地区',
    private DicModel dicCity;//'城市',

    private Date refreshDatetime;//作用每5分钟重新获取用户信息，以及角色权限

    //修改个人资料检查
    public String loginCheck(){
        if(StringUtils.isBlank(this.user_nickname)){
            return "昵称"+errorNotBlank;
        }
        this.user_nickname=this.user_nickname.trim();
        if(this.user_nickname.length()>15){
            return "昵称不能大于15个字符";
        }

        if(this.user_career_state==null || (
                this.user_career_state!=1 && this.user_career_state!=0
                )){
            return "职业"+errorNotSet;
        }

        try{
            if(this.user_avatar_file!=null && !this.user_avatar_file.isEmpty()){
                InputStream isPictureFile = this.user_avatar_file.getInputStream();
                byte[] pictureData = new byte[(int) this.user_avatar_file.getSize()];
                isPictureFile.read(pictureData);
                this.user_avatar=pictureData;
            }
        }catch (Exception ex){
            System.out.println(ex.toString());
        }

        if(this.user_nation==null){
            this.user_nation=0;
        }
        if(this.user_province==null){
            this.user_province=0;
        }
        if(this.user_city==null){
            this.user_city=0;
        }

        return "";
    }
    //注册检查
    public String registerCheck(){
        if(StringUtils.isBlank(this.user_email)){
            return "邮箱"+errorNotBlank;
        }
        this.user_email=this.user_email.trim();
        if(!EmailUtil.isEmail(this.user_email)){
            return "邮箱格式不正确";
        }

        if(StringUtils.isBlank(this.user_password)){
            return "密码"+errorNotBlank;
        }
        this.user_password=this.user_password.trim();
        if(this.user_password.length()<6){
            return "密码不能少于6位";
        }

        if(StringUtils.isBlank(this.user_nickname)){
            return "昵称"+errorNotBlank;
        }
        this.user_nickname=this.user_nickname.trim();
        if(this.user_nickname.length()>15){
            return "昵称不能大于15个字符";
        }

        return "";
    }

    /**
     * 邮箱登录检查
     * @return
     */
    public String loginEmailCheck(){
        if(StringUtils.isBlank(this.user_email)){
            return "邮箱"+errorNotBlank;
        }
        this.user_email=this.user_email.trim();
        if(!this.user_email.equals("admin")){
            if(!EmailUtil.isEmail(this.user_email)){
                return "邮箱格式不正确";
            }
        }

        if(StringUtils.isBlank(this.user_password)){
            return "密码"+errorNotBlank;
        }
        this.user_password=this.user_password.trim();
        if(this.user_password.length()<6){
            return "密码不能少于6位";
        }
        return "";
    }

    //第三方登录 type类型（1微博 2qq 3微信）uid 第三方id nice_name 昵称
    public static UserModel getBySocialApp(Integer type, String uid,String nice_name){
        UserModel _u=new UserModel();
        _u.setUser_socialapp_name(nice_name);
        _u.setUser_socialapp_id(uid);
        if(type.equals(1)){
            _u.setUser_socialapp_type("weibo");
        }else if(type.equals(2)){
            _u.setUser_socialapp_type("qq");
        }if(type.equals(3)){
            _u.setUser_socialapp_type("wechat");
        }
        return _u;
    }

    public void searchCheck(){
        if(this.user_nickname!=null){
            this.user_nickname=this.user_nickname.trim();
        }
        if(this.user_email!=null){
            this.user_email=this.user_email.trim();
        }
        if(this.user_id!=null && this.user_id<=0){
            this.user_id=null;
        }
        if(this.role_id!=null && this.role_id<=0){
            this.role_id=null;
        }
    }

    //检查操作权限
    public String checkAuth(AuthorityEnum auth){
        if(this.getSystem_account()!=null && this.getSystem_account().equals(1)){
            //超级管理
            return "";
        }
        if(this.authIdList!=null && this.authIdList.contains(auth.getI())){
            return "";
        }
        return "没有"+auth.getValue()+"权限";
    }
}

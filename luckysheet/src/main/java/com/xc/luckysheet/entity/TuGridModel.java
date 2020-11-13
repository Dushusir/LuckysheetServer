package com.xc.luckysheet.entity;


import com.xc.luckysheet.entity.enummodel.GridTypeEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 1
 * Date: 17-12-15
 * Time: 上午11:34
 * To change this template use File | Settings | File Templates.
 */
@Data
public class TuGridModel implements BaseModel{
    private String list_id;
    private String parent_list_id;// 上一级id ，等级为0  ,-1 回收站

    private String grid_name;// '表格名称',
    private String grid_introduction;// '表格说明',
    private byte[] grid_thumb;// '缩略图',
    private Long grid_creator;// '创建人id',
    private GridTypeEnum grid_type;// '表格类型 表格类型 ;dir 0;tu、1;xls、2;csv、3;xlsx、4;txt、5;

    private String mongodbkey;// 'mongodbkey',
    private Integer grid_statistic_sheet;// 'sheet数量',
    private String grid_filepath;// '源文件路径',（此处为 用户id/记录id.文件类型）  路径为 配置/用户id/记录id.文件类型

    private Date grid_create_time;// '创建时间',
    private Date grid_update_time;// '最后修改时间',
    private Integer grid_status;// '状态 1正常 -1垃圾箱',
    private String grid_datashow_key;// '数据秀发布key',
    private Integer grid_datashow_public;// '是否发布数据秀 0否 1是',
    private Integer grid_share_status;// '是否分享 0否 1是',
    private Integer allow_share_status;//是否允许分享  1允许(默认) 0不允许
    private Long category_id;//分类id
    private String grid_tag;// '标签tag', ","分割

    private Integer grid_statistic_favorite;// '收藏次数',
    private Integer grid_statistic_comment;// '评论次数',
    private Integer grid_statistic_like;// '点赞次数',
    private Long grid_statistic_view;// '浏览次数',

    private Long uploadsize;//上传文件的大小 字节
    private Long mongodbsize;//mongodb的大小 字节



    //不存储
    private List<TuGridModel> tuGridModels=new ArrayList<TuGridModel>();//下级文件
    private UserModel userModel; //用户信息
    private MultipartFile uploadFile;//上传的文件
    private String grid_thumb_base64;//图像
    private List<String> tags;//标签（上传文件时使用）
    private String category_path;//分类路径 (执行存储过程获取)
    //搜索使用
    private Date start_grid_create_time;// '创建时间',
    private Date end_grid_create_time;// '创建时间',
    private Date start_grid_update_time;// '最后修改时间',
    private Date end_grid_update_time;// '最后修改时间',

    private Long dirsize;//文件夹大小
    private String uploadsizeStr;//上传文件的大小
    private String mongodbsizeStr;//mongodb的大小
    private String dirsizeStr;//文件夹大小



}

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

    /**
     * 表格名称
     */
    private String grid_name;
    /**
     *  '缩略图'
     */
    private byte[] grid_thumb;,

}

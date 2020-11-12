package com.xc.common.entity.base;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Administrator
 */
@Data
public class BaseEntity implements Serializable,Cloneable {

    //@ApiModelProperty(hidden = true)
    @TableId(type = IdType.AUTO)
    protected Long id;

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    protected Date createTm;

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    protected Date updateTm;

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    protected String createUser;

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    protected String updateUser;

    @ApiModelProperty(hidden = true)
    @TableLogic(value = "0",delval = "1")
    @TableField(fill = FieldFill.INSERT)
    protected String isDelete;

    /**
     * 判断自定义
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.judecodes.maildatasource.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
//如果你有 createTime / updateTime 这种公共字段，可以用 MyBatis-Plus 的自动填充，实现“插入/更新自动填值”。
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 是否删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    /**
     * 乐观锁版本号
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer lockVersion;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}

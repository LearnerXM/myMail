package com.judecodes.maildatasource.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
//如果你有 createTime / updateTime 这种公共字段，可以用 MyBatis-Plus 的自动填充，实现“插入/更新自动填值”。
@Getter
@Setter

public class BaseEntity implements Serializable {
    @Serial
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
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}

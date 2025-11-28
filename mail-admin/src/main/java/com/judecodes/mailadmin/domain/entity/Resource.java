package com.judecodes.mailadmin.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 后台资源表
 * </p>
 *
 * @author judecodes
 * @since 2025-11-28
 */
@Getter
@Setter
@ToString
@TableName("ums_resource")
public class Resource {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 资源名称
     */
    @TableField("name")
    private String name;

    /**
     * 资源URL
     */
    @TableField("url")
    private String url;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 资源分类ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableField("deleted")
    private Boolean deleted;

    /**
     * 版本号
     */
    @TableField("lock_version")
    private Integer lockVersion;
}

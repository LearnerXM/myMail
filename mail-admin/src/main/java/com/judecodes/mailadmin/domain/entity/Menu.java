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
 * 后台菜单表
 * </p>
 *
 * @author judecodes
 * @since 2025-11-26
 */
@Getter
@Setter
@ToString
@TableName("ums_menu")
public class Menu {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 菜单名称
     */
    @TableField("title")
    private String title;

    /**
     * 菜单级数
     */
    @TableField("level")
    private Integer level;

    /**
     * 菜单排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 前端名称
     */
    @TableField("name")
    private String name;

    /**
     * 前端图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 前端隐藏
     */
    @TableField("hidden")
    private Integer hidden;

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

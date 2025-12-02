package com.judecodes.mailadmin.domain.entity;


import com.baomidou.mybatisplus.annotation.TableField;

import com.baomidou.mybatisplus.annotation.TableName;
import com.judecodes.maildatasource.entity.BaseEntity;
import lombok.*;



/**
 * <p>
 * 后台用户角色表
 * </p>
 *
 * @author judecodes
 * @since 2025-11-28
 */
@Getter
@Setter
@ToString
@AllArgsConstructor  // 这个很关键
@NoArgsConstructor
@TableName("ums_role")
public class Role extends BaseEntity {



    /**
     * 角色编码，如 SUPER_ADMIN
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 后台用户数量
     */
    @TableField("admin_count")
    private Integer adminCount;



    /**
     * 启用状态：0->禁用；1->启用
     */
    @TableField("status")
    private Integer status;

    @TableField("sort")
    private Integer sort;

}

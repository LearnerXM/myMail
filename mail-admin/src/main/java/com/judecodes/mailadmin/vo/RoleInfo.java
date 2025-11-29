package com.judecodes.mailadmin.vo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleInfo {

    private Long id;

    /**
     * 角色编码，如 SUPER_ADMIN
     */

    private String roleCode;

    /**
     * 名称
     */

    private String name;

    /**
     * 描述
     */

    private String description;

    /**
     * 后台用户数量
     */

    private Integer adminCount;

    /**
     * 创建时间
     */

    private LocalDateTime createTime;

    /**
     * 启用状态：0->禁用；1->启用
     */

    private Integer status;


    private Integer sort;

    /**
     * 更新时间
     */

    private LocalDateTime updateTime;


}

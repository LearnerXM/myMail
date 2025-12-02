package com.judecodes.mailadmin.param;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateRoleParam {
    /**
     * 角色编码，如 SUPER_ADMIN
     */
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 名称
     */
    @NotBlank(message = "角色名称不能为空")
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
     * 启用状态：0->禁用；1->启用
     */
    @NotBlank(message = "启用状态不能为空")
    private Integer status;


    private Integer sort;
}

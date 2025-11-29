package com.judecodes.mailadmin.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AdminRoleUpdateParam {

    @NotNull(message = "管理员ID不能为空")
    private Long adminId;

    @NotNull(message = "角色ID列表不能为空")
    private List<Long> roleIdList;   // 可以允许为空列表，表示清空角色，看业务需求
}

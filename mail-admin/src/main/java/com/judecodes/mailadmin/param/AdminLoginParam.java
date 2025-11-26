package com.judecodes.mailadmin.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminLoginParam {
    /**
     * 账号
     */
    @NotBlank
    private String username;
    /**
     * 密码
     */
    @NotBlank
    private String password;

    /**
     * 记住我
     */
    private Boolean rememberMe;
}

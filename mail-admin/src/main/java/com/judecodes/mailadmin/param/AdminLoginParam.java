package com.judecodes.mailadmin.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(min = 6, max = 20, message = "密码长度必须在6-20位之间")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$",
            message = "密码必须包含字母和数字"
    )
    private String password;

    /**
     * 记住我
     */
    private Boolean rememberMe;
}

package com.judecodes.mailauth.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginParam {
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
}

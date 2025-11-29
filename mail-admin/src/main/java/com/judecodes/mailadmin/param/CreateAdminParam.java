package com.judecodes.mailadmin.param;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAdminParam {
    @NotEmpty
    @Size(min = 5, max = 20, message = "账号长度必须在6-20位之间")
    private String username;

    @NotEmpty
    @Size(min = 6, max = 20, message = "密码长度必须在6-20位之间")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$",
            message = "密码必须包含字母和数字"
    )
    private String password;

    /**
     * 头像
     */
    @NotEmpty
    private String icon;

    /**
     * 邮箱
     */
    @Email
    @NotEmpty
    private String email;

    /**
     * 昵称
     */
    @NotEmpty
    private String nickName;

    /**
     * 备注信息
     */
    @NotEmpty
    private String note;
}

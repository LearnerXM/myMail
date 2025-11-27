package com.judecodes.mailadmin.param;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class AdminParam {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    /**
     * 头像
     */

    private String icon;

    /**
     * 邮箱
     */
    @Email
    private String email;

    /**
     * 昵称
     */

    private String nickName;

    /**
     * 备注信息
     */

    private String note;
}

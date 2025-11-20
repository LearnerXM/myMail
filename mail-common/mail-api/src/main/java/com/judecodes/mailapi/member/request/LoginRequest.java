package com.judecodes.mailapi.member.request;

import com.judecodes.mailbase.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LoginRequest extends BaseRequest {

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

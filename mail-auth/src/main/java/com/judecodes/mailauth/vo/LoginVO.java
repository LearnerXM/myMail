package com.judecodes.mailauth.vo;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class LoginVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户标识，如用户ID
     */
    private Long userId;
    /**
     * 访问令牌
     */
    private String token;

    /**
     * 令牌过期时间
     */
    private Long tokenExpiration;



}

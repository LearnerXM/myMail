package com.judecodes.mailadmin.vo;

import cn.dev33.satoken.stp.StpUtil;
import com.judecodes.mailadmin.domain.entity.Admin;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AdminLoginVO implements Serializable {
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

    public AdminLoginVO(Admin admin) {
        this.userId= admin.getId();
        this.token= StpUtil.getTokenValue();
        this.tokenExpiration= StpUtil.getTokenSessionTimeout();
    }
}

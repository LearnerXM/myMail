package com.judecodes.mailapi.member.response.data;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class BasicMemberInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 用户标识，如用户ID
     */
    private Long id;

    /**
     * 昵称
     */

    private String nickname;

    /**
     * 头像
     */

    private String icon;
}

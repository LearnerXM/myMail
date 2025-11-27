package com.judecodes.mailadmin.vo;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class AdminInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private String username;



    /**
     * 头像
     */

    private String icon;

    /**
     * 邮箱
     */

    private String email;

    /**
     * 昵称
     */

    private String nickName;



}

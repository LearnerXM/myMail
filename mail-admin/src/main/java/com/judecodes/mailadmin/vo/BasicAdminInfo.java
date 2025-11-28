package com.judecodes.mailadmin.vo;



import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class BasicAdminInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long id;



    /**
     * 头像
     */

    private String icon;



    /**
     * 昵称
     */

    private String nickName;



}

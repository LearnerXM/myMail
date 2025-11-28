package com.judecodes.mailadmin.vo;


import lombok.Data;


import java.io.Serial;
import java.time.LocalDateTime;

@Data
public class AdminInfo extends BasicAdminInfo{

    @Serial
    private static final long serialVersionUID = 1L;


    private String username;

    /**
     * 邮箱
     */

    private String email;



    /**
     * 备注信息
     */

    private String note;


    /**
     * 最后登录时间
     */

    private LocalDateTime loginTime;

    /**
     * 帐号启用状态：0->禁用；1->启用
     */

    private Integer status;
    /**
     * 创建时间
     */

    private LocalDateTime createTime;
}

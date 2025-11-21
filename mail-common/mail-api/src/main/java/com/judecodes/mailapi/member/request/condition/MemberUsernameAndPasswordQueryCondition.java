package com.judecodes.mailapi.member.request.condition;


import lombok.Data;

@Data
public class MemberUsernameAndPasswordQueryCondition implements MemberQueryCondition {

    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

}

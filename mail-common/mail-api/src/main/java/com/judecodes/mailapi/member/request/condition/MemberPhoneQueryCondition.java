package com.judecodes.mailapi.member.request.condition;


import lombok.Data;

@Data
public class MemberPhoneQueryCondition implements MemberQueryCondition {

    private static final long serialVersionUID = 1L;
    /**
     * 手机号
     */
    private String phone;
}

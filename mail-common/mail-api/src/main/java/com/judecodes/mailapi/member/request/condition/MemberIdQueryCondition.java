package com.judecodes.mailapi.member.request.condition;

import lombok.Data;

@Data
public class MemberIdQueryCondition implements MemberQueryCondition{

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long memberId;
}

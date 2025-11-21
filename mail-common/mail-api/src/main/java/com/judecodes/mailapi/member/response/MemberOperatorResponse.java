package com.judecodes.mailapi.member.response;

import com.judecodes.mailapi.member.response.data.MemberInfo;
import com.judecodes.mailbase.response.BaseResponse;
import lombok.Data;

@Data
public class MemberOperatorResponse extends BaseResponse {


    private static final long serialVersionUID = 1L;

    /**
     * 用户标识，如用户ID
     */
    private MemberInfo memberInfo;


}

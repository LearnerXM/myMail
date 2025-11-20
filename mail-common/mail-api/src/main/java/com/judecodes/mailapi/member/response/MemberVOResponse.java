package com.judecodes.mailapi.member.response;

import com.judecodes.mailbase.response.BaseResponse;
import lombok.Data;

@Data
public class MemberVOResponse extends BaseResponse {
    /**
     * 用户标识，如用户ID
     */
    private Long userId;


}

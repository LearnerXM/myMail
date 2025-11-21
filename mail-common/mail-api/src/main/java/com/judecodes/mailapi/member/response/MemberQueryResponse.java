package com.judecodes.mailapi.member.response;

import com.judecodes.mailbase.response.BaseResponse;
import lombok.Data;


@Data
public class MemberQueryResponse<T> extends BaseResponse {


    private static final long serialVersionUID = 1L;


    private T data;
}

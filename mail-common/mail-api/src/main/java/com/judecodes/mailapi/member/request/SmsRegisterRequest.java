package com.judecodes.mailapi.member.request;

import com.judecodes.mailbase.request.BaseRequest;
import lombok.Data;

@Data
public class SmsRegisterRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 验证码
     */
    private String code;
}

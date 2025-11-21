package com.judecodes.mailapi.notice.resquest;

import com.judecodes.mailapi.notice.constant.SmsType;
import com.judecodes.mailbase.request.BaseRequest;
import lombok.Data;

@Data
public class SendCodeRequest extends BaseRequest {
    private String phone;
    private SmsType smsType;
}

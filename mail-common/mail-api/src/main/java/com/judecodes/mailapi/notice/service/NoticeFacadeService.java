package com.judecodes.mailapi.notice.service;

import com.judecodes.mailapi.notice.response.NoticeResponse;
import com.judecodes.mailapi.notice.resquest.SendCodeRequest;

public interface NoticeFacadeService {

    NoticeResponse sendCode(SendCodeRequest sendCodeRequest);

}

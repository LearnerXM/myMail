package com.judecodes.mailapi.notice.service;

import com.judecodes.mailapi.notice.response.NoticeResponse;

public interface NoticeFacadeService {

    NoticeResponse sendCode(String phone);

}

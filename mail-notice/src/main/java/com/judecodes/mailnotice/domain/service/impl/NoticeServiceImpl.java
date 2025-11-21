package com.judecodes.mailnotice.domain.service.impl;

import com.judecodes.mailbase.exception.BizErrorCode;
import com.judecodes.mailbase.exception.BizException;
import com.judecodes.mailnotice.constant.NoticeState;
import com.judecodes.mailnotice.constant.NoticeType;
import com.judecodes.mailnotice.domain.entity.Notice;
import com.judecodes.mailnotice.infrastructure.mapper.NoticeMapper;
import com.judecodes.mailnotice.domain.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通知表 服务实现类
 * </p>
 *
 * @author judecodes
 * @since 2025-11-19
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {


    private static final String SMS_NOTICE_TITLE = "验证码";


    @Override
    public Notice saveCode(String phone, String code,String smsType) {
        Notice notice = Notice.builder()
                .noticeTitle(SMS_NOTICE_TITLE)
                .noticeContent(code)
                .targetAddress(phone)
                .smsType(smsType)
                .state(NoticeState.INIT.name())
                .noticeType(NoticeType.SMS.name())
                .build();
        boolean result = save(notice);
        if (!result) {
            throw new BizException(BizErrorCode.NOTICE_SAVE_FAILED);
        }
        return notice;
    }
}

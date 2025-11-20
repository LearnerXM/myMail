package com.judecodes.mailnotice.domain.service.impl;

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

    @Override
    public Notice saveCode(String phone, String code) {
        Notice notice = Notice.builder()
                .noticeContent(code)
                .targetAddress(phone)
                .state(NoticeState.INIT.name())
                .noticeType(NoticeType.SMS.name())
                .build();
        save(notice);
        return notice;
    }
}

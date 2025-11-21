package com.judecodes.mailnotice.facade;


import cn.hutool.core.util.RandomUtil;
import com.judecodes.mailapi.notice.constant.SmsType;
import com.judecodes.mailapi.notice.response.NoticeResponse;
import com.judecodes.mailapi.notice.resquest.SendCodeRequest;
import com.judecodes.mailapi.notice.service.NoticeFacadeService;
import com.judecodes.mailbase.exception.BizErrorCode;
import com.judecodes.mailbase.exception.SystemException;
import com.judecodes.maillimiter.server.RateLimiter;
import com.judecodes.mailnotice.domain.entity.Notice;
import com.judecodes.mailnotice.domain.service.NoticeService;
import com.judecodes.mailrpc.facade.Facade;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;




@DubboService(version = "1.0.0")
public class NoticeFacadeServiceImpl implements NoticeFacadeService {

    @Autowired
    private RateLimiter rateLimiter;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private NoticeService noticeService;

    @Facade
    @Override
    public NoticeResponse sendCode(SendCodeRequest sendCodeRequest) {

        String phone = sendCodeRequest.getPhone();
        SmsType smsType = sendCodeRequest.getSmsType();
        boolean result = rateLimiter.tryAcquire(phone, 1, 60);

        if (!result){
            throw new SystemException(BizErrorCode.SEND_NOTICE_DUPLICATED);
        }

        String code = RandomUtil.randomNumbers(6);

        stringRedisTemplate.opsForValue().set(smsType.buildRedisKey(phone), code, 5, TimeUnit.MINUTES);

        Notice notice = noticeService.saveCode(phone, code, smsType.getCode());
        if (notice==null){
            throw new SystemException(BizErrorCode.NOTICE_SAVE_FAILED);
        }
        // TODO: send notice
        //使用虚拟线程通过smsService服务发送短信，并更新短信表

        NoticeResponse noticeResponse = new NoticeResponse();
        noticeResponse.setSuccess(true);
        return noticeResponse;
    }
}

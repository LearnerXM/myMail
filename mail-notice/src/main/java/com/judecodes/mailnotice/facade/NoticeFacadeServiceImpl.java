package com.judecodes.mailnotice.facade;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import com.judecodes.mailapi.notice.service.NoticeFacadeService;
import com.judecodes.maillimiter.server.RateLimiter;
import com.judecodes.mailnotice.domain.entity.Notice;
import com.judecodes.mailnotice.domain.service.NoticeService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

import static com.judecodes.mailapi.notice.constant.NoticeConstant.CODE_KEY_PREFIX;


@DubboService(version = "1.0.0")
public class NoticeFacadeServiceImpl implements NoticeFacadeService {

    @Autowired
    private RateLimiter rateLimiter;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private NoticeService noticeService;
    @Override
    public Boolean sendCode(String phone) {
        boolean result = rateLimiter.tryAcquire(phone, 1, 60);
        Assert.isTrue(result, "发送过于频繁，请稍后再试");
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(CODE_KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
        Notice notice = noticeService.saveCode(phone, code);

        // TODO: send notice
        return notice != null;
    }
}

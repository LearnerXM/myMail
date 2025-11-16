package com.judecodes.maillimiter.server;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;


public class RedissonRateLimiter implements RateLimiter {

    private final RedissonClient redissonClient;

    private static final String LIMIT_KEY_PREFIX = "rate:limit:";

    public RedissonRateLimiter(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }


    @Override
    public boolean tryAcquire(String key, int limit, int windowSize) {
        // 基本防御判断
        if (limit <= 0 || windowSize <= 0) {
            return false;
        }

        // 每个业务 key 对应一个 Redisson 限流器
        String limiterName = LIMIT_KEY_PREFIX + key;
        RRateLimiter limiter = redissonClient.getRateLimiter(limiterName);

        // 设置限流规则：
        // RATE_TYPE = OVERALL：集群所有实例共享限流
        // limit：窗口内最大通过次数
        // windowSize：窗口时间，单位秒
        limiter.trySetRate(
                RateType.OVERALL,
                limit,
                windowSize,
                RateIntervalUnit.SECONDS
        );

        // 尝试获取一个令牌
        return limiter.tryAcquire();
    }

}

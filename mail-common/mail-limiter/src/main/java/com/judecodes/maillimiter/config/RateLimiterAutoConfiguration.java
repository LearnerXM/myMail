package com.judecodes.maillimiter.config;

import com.judecodes.maillimiter.server.RateLimiter;
import com.judecodes.maillimiter.server.RedissonRateLimiter;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
@ConditionalOnClass(RedissonClient.class)
public class RateLimiterAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RateLimiterAutoConfiguration.class);

    public RateLimiterAutoConfiguration() {
        log.info("RateLimiterAutoConfiguration initialized (Redisson)");
    }

    @Bean
    @ConditionalOnMissingBean(RateLimiter.class)
    public RateLimiter rateLimiter(RedissonClient redissonClient) {
        log.info("Creating RedissonRateLimiter with prefix=rate:limit:, type=OVERALL");
        return new RedissonRateLimiter(redissonClient);
    }
}
package com.judecodes.maillimiter.server;

/**
 * 通用限流器接口
 */
public interface RateLimiter {


    /**
     * 尝试获取一次访问许可
     *
     * @param key        限流维度 key，例如 "sms:13800138000"
     * @param limit      在窗口内允许的最大次数，例如 1
     * @param windowSize 窗口大小，单位：秒，例如 60 表示 60 秒窗口
     * @return true 表示通过（允许执行），false 表示被限流
     */
    boolean tryAcquire(String key, int limit, int windowSize);

}

package com.judecodes.mailapi.notice.constant;



/**
 * 短信类型枚举
 * 用于区分不同业务场景下的短信，比如：
 * - 认证授权（登录 / 注册 / 登录确认等）
 * - 修改密码
 */
public enum SmsType {

    /**
     * 认证授权业务短信
     */
    AUTH("AUTH", "sms:auth:", "认证授权短信"),

    /**
     * 修改密码业务短信
     */
    PASSWORD_CHANGE("PASSWORD_CHANGE", "sms:pwd_change:", "密码修改短信");

 // 存到数据库 sms_type 列的值：AUTH / PASSWORD_CHANGE
    private final String code;

    // 用于 Redis key 前缀（可以继续用小写，更符合常见 Redis 风格）
    private final String redisKeyPrefix;

    // 描述信息
    private final String desc;

    SmsType(String code, String redisKeyPrefix, String desc) {
        this.code = code;
        this.redisKeyPrefix = redisKeyPrefix;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getRedisKeyPrefix() {
        return redisKeyPrefix;
    }

    public String getDesc() {
        return desc;
    }

    public String buildRedisKey(String phone) {
        return this.redisKeyPrefix + phone;
    }

}

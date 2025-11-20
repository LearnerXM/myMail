package com.judecodes.mailmember.infrastructure.util;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 用户密码加密 & 校验工具类
 */
public final class PasswordUtils {

    // 线程安全，可以全局单例使用
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private PasswordUtils() {
    }

    /**
     * 加密明文密码（注册 / 修改密码时调用）
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码（可直接存数据库）
     */
    public static String encode(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    /**
     * 校验密码是否正确（登录时调用）
     *
     * @param rawPassword     用户输入的明文密码
     * @param encodedPassword 数据库中存的加密密码
     * @return true：匹配成功；false：不正确
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }
}

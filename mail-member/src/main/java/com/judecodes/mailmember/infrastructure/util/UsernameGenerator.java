package com.judecodes.mailmember.infrastructure.util;

import java.security.SecureRandom;

public final class UsernameGenerator {

    private static final String PREFIX = "mail";
    private static final int RANDOM_DIGITS = 10;

    private static final SecureRandom RANDOM = new SecureRandom();

    private UsernameGenerator() {
    }

    /**
     * 生成一个形如 mailXXXXXXXXXX 的用户名
     */
    public static String generate() {
        StringBuilder sb = new StringBuilder(PREFIX);
        for (int i = 0; i < RANDOM_DIGITS; i++) {
            int digit = RANDOM.nextInt(10); // 0-9
            sb.append(digit);
        }
        return sb.toString();
    }
}

package com.judecodes.maildatasource.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 随机初始密码生成工具
 */
public final class RandomPasswordGenerator {

    // 可按需调整（去掉 0 O 1 l 等易混淆）
    private static final String UPPER = "ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijkmnpqrstuvwxyz";
    private static final String DIGITS = "23456789";
    private static final String SPECIAL = "!@#$%^&*";

    // 所有字符池
    private static final String ALL = UPPER + LOWER + DIGITS + SPECIAL;

    // 安全随机数
    private static final SecureRandom RANDOM = new SecureRandom();

    private RandomPasswordGenerator() {
    }

    /**
     * 生成一个强度较高的随机密码
     * 规则：
     * - 至少包含 1 个大写、1 个小写、1 个数字、1 个特殊字符
     * - 总长度默认为 12
     */
    public static String generate() {
        return generate(12);
    }

    /**
     * 生成指定长度的随机密码
     *
     * @param length 密码长度，建议 >= 8，推荐 >= 12
     */
    public static String generate(int length) {
        if (length < 4) {
            throw new IllegalArgumentException("密码长度至少为 4，实际：" + length);
        }

        List<Character> pwdChars = new ArrayList<>(length);

        // 先保证每类字符至少一个
        pwdChars.add(randomChar(UPPER));
        pwdChars.add(randomChar(LOWER));
        pwdChars.add(randomChar(DIGITS));
        pwdChars.add(randomChar(SPECIAL));

        // 剩下的随机从 ALL 中选
        for (int i = 4; i < length; i++) {
            pwdChars.add(randomChar(ALL));
        }

        // 打乱顺序，避免前 4 位固定为四种字符
        Collections.shuffle(pwdChars, RANDOM);

        // 拼成字符串
        StringBuilder sb = new StringBuilder(length);
        for (char c : pwdChars) {
            sb.append(c);
        }
        return sb.toString();
    }

    private static char randomChar(String chars) {
        int index = RANDOM.nextInt(chars.length());
        return chars.charAt(index);
    }
}

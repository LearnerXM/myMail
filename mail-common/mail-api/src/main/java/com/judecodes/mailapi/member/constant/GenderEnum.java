package com.judecodes.mailapi.member.constant;

import lombok.Getter;

@Getter
public enum GenderEnum {

    SECRET(0, "保密"),
    MALE(1, "男性"),
    FEMALE(2, "女性");

    private final int code;
    private final String desc;

    GenderEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据 code 获取枚举
     */
    public static GenderEnum getGenderEnumByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (GenderEnum gender : values()) {
            if (gender.code == code) {
                return gender;
            }
        }
        return null; // 或者抛异常，也可以改成返回 SECRET
    }
}
package com.judecodes.mailbase.constant;

import lombok.Getter;

@Getter
public enum AdminRoleEnum {

    /**
     * 商品管理员
     */
    PRODUCT_ADMIN(1, "商品管理员"),

    /**
     * 订单管理员
     */
    ORDER_ADMIN(2, "订单管理员"),

    /**
     * 超级管理员
     */
    SUPER_ADMIN(5, "超级管理员");

    private final int code;
    private final String desc;

    AdminRoleEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据 code 获取枚举
     */
    public static AdminRoleEnum fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (AdminRoleEnum value : AdminRoleEnum.values()) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }
}

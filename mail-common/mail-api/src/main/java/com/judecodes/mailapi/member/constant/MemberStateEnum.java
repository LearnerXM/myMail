package com.judecodes.mailapi.member.constant;

import lombok.Getter;

@Getter
public enum MemberStateEnum {
    DISABLED(0, "禁用"),
    ENABLED(1, "启用"),
    LOCKED(2, "锁定"),
    DELETED(3, "已删除");

    private final int code;
    private final String desc;

    MemberStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据 code 获取枚举
     */
    public static MemberStateEnum getMemberStateEnumByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (MemberStateEnum status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null; // 也可以改成返回 DISABLED 或抛业务异常
    }

    /**
     * 是否是启用状态
     */
    public boolean isEnabled() {
        return this == ENABLED;
    }
}

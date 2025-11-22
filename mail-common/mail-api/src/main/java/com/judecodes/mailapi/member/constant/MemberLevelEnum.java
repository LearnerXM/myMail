package com.judecodes.mailapi.member.constant;

import lombok.Getter;

@Getter
public enum MemberLevelEnum {

    GOLD(1L, "黄金会员", 1000),
    PLATINUM(2L, "白金会员", 5000),
    DIAMOND(3L, "钻石会员", 15000),
    NORMAL(4L, "普通会员", 1);

    /**
     * 等级ID，对应 member_level_id
     */
    private final Long id;

    /**
     * 等级名称
     */
    private final String desc;

    /**
     * 达到该等级所需的最小成长值
     */
    private final Integer growthThreshold;

    MemberLevelEnum(Long id, String desc, Integer growthThreshold) {
        this.id = id;
        this.desc = desc;
        this.growthThreshold = growthThreshold;
    }

    /**
     * 通过等级ID获取枚举
     */
    public static MemberLevelEnum getById(Long id) {
        if (id == null) {
            return null;
        }
        for (MemberLevelEnum level : values()) {
            if (level.id.equals(id)) {
                return level;
            }
        }
        return null;
    }

    /**
     * 根据成长值计算当前等级（简单从低到高匹配）
     */
    public static MemberLevelEnum fromGrowth(int growth) {
        MemberLevelEnum result = NORMAL;  // 默认普通会员
        for (MemberLevelEnum level : values()) {
            if (growth >= level.growthThreshold) {
                // 成长值达到该等级门槛，就暂记为当前等级
                result = level;
            }
        }
        return result;
    }


}

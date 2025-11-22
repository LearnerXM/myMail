package com.judecodes.mailapi.member.constant;


public class MemberConstants {

    /**
     * 会员修改密码时的验证Token
     */
    public static final  String PWD_CHANGE_TOKEN = "pwd:change:token:";

    /**
     * 新注册会员默认积分
     */
    public static final int DEFAULT_INTEGRATION = 0;

    /**
     * 新注册会员默认成长值
     */
    public static final int DEFAULT_GROWTH = 1;

    /**
     * 新注册会员默认等级ID：普通会员
     */

     public static final Long DEFAULT_MEMBER_LEVEL_ID = MemberLevelEnum.NORMAL.getId();

    /**
     * 新注册会员默认剩余抽奖次数
     */
    public static final int DEFAULT_LUCKY_COUNT = 0;

    /**
     * 新注册会员默认历史积分数量
     */
    public static final int DEFAULT_HISTORY_INTEGRATION = 0;

    private MemberConstants() {
    }
}

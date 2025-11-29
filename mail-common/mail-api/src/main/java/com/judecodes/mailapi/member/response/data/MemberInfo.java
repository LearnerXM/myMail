package com.judecodes.mailapi.member.response.data;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberInfo extends BasicMemberInfo{

    private Long memberLevelId;

    /**
     * 用户名
     */

    private String username;



    /**
     * 手机号码
     */

    private String phone;





    /**
     * 性别：0->未知；1->男；2->女
     */

    private Integer gender;

    /**
     * 生日
     */

    private LocalDate birthday;

    /**
     * 所在城市
     */

    private String city;

    /**
     * 职业
     */

    private String job;

    /**
     * 个性签名
     */

    private String personalizedSignature;

    /**
     * 用户来源
     */

    private Integer sourceType;

    /**
     * 积分
     */

    private Integer integration;

    /**
     * 成长值
     */

    private Integer growth;

    /**
     * 剩余抽奖次数
     */

    private Integer luckeyCount;

    /**
     * 历史积分数量
     */

    private Integer historyIntegration;
}

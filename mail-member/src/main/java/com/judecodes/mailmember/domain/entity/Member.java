package com.judecodes.mailmember.domain.entity;


import com.baomidou.mybatisplus.annotation.TableField;

import com.baomidou.mybatisplus.annotation.TableName;
import com.judecodes.maildatasource.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author judecodes
 * @since 2025-11-18
 */
@Getter
@Setter
@ToString
@TableName("ums_member")
public class Member extends BaseEntity {



    @TableField("member_level_id")
    private Long memberLevelId;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 手机号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 帐号启用状态:0->禁用；1->启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 头像
     */
    @TableField("icon")
    private String icon;

    /**
     * 性别：0->未知；1->男；2->女
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 生日
     */
    @TableField("birthday")
    private LocalDate birthday;

    /**
     * 所在城市
     */
    @TableField("city")
    private String city;

    /**
     * 职业
     */
    @TableField("job")
    private String job;

    /**
     * 个性签名
     */
    @TableField("personalized_signature")
    private String personalizedSignature;

    /**
     * 用户来源
     */
    @TableField("source_type")
    private Integer sourceType;

    /**
     * 积分
     */
    @TableField("integration")
    private Integer integration;

    /**
     * 成长值
     */
    @TableField("growth")
    private Integer growth;

    /**
     * 剩余抽奖次数
     */
    @TableField("luckey_count")
    private Integer luckeyCount;

    /**
     * 历史积分数量
     */
    @TableField("history_integration")
    private Integer historyIntegration;
}

package com.judecodes.mailnotice.domain.entity;


import com.baomidou.mybatisplus.annotation.TableField;

import com.baomidou.mybatisplus.annotation.TableName;
import com.judecodes.maildatasource.entity.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 通知表
 * </p>
 *
 * @author judecodes
 * @since 2025-11-19
 */
@Getter
@Setter
@Builder
@AllArgsConstructor  // 这个很关键
@NoArgsConstructor
@TableName("ums_notice")
public class Notice extends BaseEntity {



    /**
     * 通知标题
     */
    @TableField("notice_title")
    private String noticeTitle;

    /**
     * 通知内容
     */
    @TableField("notice_content")
    private String noticeContent;

    /**
     * 通知类型
     */
    @TableField("notice_type")
    private String noticeType;

    /**
     * 发送成功时间
     */
    @TableField("send_success_time")
    private LocalDateTime sendSuccessTime;

    /**
     * 接收地址
     */
    @TableField("target_address")
    private String targetAddress;

    /**
     * 状态
     */
    @TableField("state")
    private String state;

    /**
     * 重试次数
     */
    @TableField("retry_times")
    private Integer retryTimes;

    /**
     * 扩展信息
     */
    @TableField("extend_info")
    private String extendInfo;

    /**
     * 短信类型：AUTH / PASSWORD_CHANGE
     */
    @TableField("sms_type")
    private String smsType;
}

package com.judecodes.mailadmin.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.judecodes.maildatasource.entity.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 后台用户表
 * </p>
 *
 * @author judecodes
 * @since 2025-11-26
 */
@Getter
@Setter
@ToString
@AllArgsConstructor  // 这个很关键
@NoArgsConstructor
@TableName("ums_admin")
public class Admin extends BaseEntity {



    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    /**
     * 头像
     */
    @TableField("icon")
    private String icon;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 备注信息
     */
    @TableField("note")
    private String note;


    /**
     * 最后登录时间
     */
    @TableField("login_time")
    private LocalDateTime loginTime;

    /**
     * 帐号启用状态：0->禁用；1->启用
     */
    @TableField("status")
    private Integer status;



}

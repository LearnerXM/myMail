package com.judecodes.mailuser.domin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author judecodes
 * @since 2025-11-17
 */
@Getter
@Setter
@ToString
@TableName("tz_user")
@Schema(name = "User", description = "用户表")
public class User {

    /**
     * 用户ID（自增主键）
     */
    @Schema(description = "用户ID（自增主键）")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户昵称
     */
    @TableField("nick_name")
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    @Schema(description = "真实姓名")
    private String realName;

    /**
     * 用户邮箱
     */
    @TableField("user_mail")
    @Schema(description = "用户邮箱")
    private String userMail;

    /**
     * 登录密码
     */
    @Schema(description = "登录密码")
    @TableField("login_password")
    private String loginPassword;

    /**
     * 支付密码
     */
    @Schema(description = "支付密码")
    @TableField("pay_password")
    private String payPassword;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    @TableField("user_mobile")
    private String userMobile;

    /**
     * 注册IP
     */
    @Schema(description = "注册IP")
    @TableField("user_regip")
    private String userRegip;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    @TableField("user_lasttime")
    private LocalDateTime userLasttime;

    /**
     * 最后登录IP
     */
    @TableField("user_lastip")
    @Schema(description = "最后登录IP")
    private String userLastip;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @TableField("user_memo")
    private String userMemo;

    /**
     * M(男) or F(女)
     */
    @TableField("sex")
    @Schema(description = "M(男) or F(女)")
    private String sex;

    /**
     * 例如：2009-11-27
     */
    @TableField("birth_date")
    @Schema(description = "例如：2009-11-27")
    private String birthDate;

    /**
     * 头像图片路径
     */
    @TableField("pic")
    @Schema(description = "头像图片路径")
    private String pic;

    /**
     * 状态 1 正常 0 无效
     */
    @TableField("status")
    @Schema(description = "状态 1 正常 0 无效")
    private Integer status;

    /**
     * 用户积分
     */
    @TableField("score")
    @Schema(description = "用户积分")
    private Integer score;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    @TableField("user_account")
    private String userAccount;

    /**
     * 实名认证状态（TRUE或FALSE）
     */
    @TableField("certification")
    @Schema(description = "实名认证状态（TRUE或FALSE）")
    private Boolean certification;

    /**
     * 身份证no
     */
    @TableField("id_card_no")
    @Schema(description = "身份证no")
    private String idCardNo;

    /**
     * 用户角色
     */
    @TableField("user_role")
    @Schema(description = "用户角色")
    private String userRole;

    /**
     * 是否逻辑删除，0为未删除，非0为已删除
     */
    @TableField("deleted")
    @Schema(description = "是否逻辑删除，0为未删除，非0为已删除")
    private Integer deleted;

    /**
     * 乐观锁版本号
     */
    @Schema(description = "乐观锁版本号")
    @TableField("lock_version")
    private Integer lockVersion;
}

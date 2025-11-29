package com.judecodes.mailadmin.infrastructure.exception;

import com.judecodes.mailbase.exception.ErrorCode;

public enum AdminErrorCode implements ErrorCode {
    /**
     * 管理员不存在
     */
    ADMIN_NOT_EXIST("ADMIN_NOT_EXIST", "管理员不存在"),

    /**
     * Token异常
     */
    TOKEN_ERROR("TOKEN_ERROR","密码Token异常"),
    /**
     * 管理员上传图片失败
     */
    ADMIN_UPLOAD_PICTURE_FAIL("ADMIN_UPLOAD_PICTURE_FAIL", "管理员上传图片失败"),

    /**
     * 管理员查询失败
     */
    ADMIN_QUERY_FAIL("ADMIN_QUERY_FAIL", "管理员查询失败"),

    /**
     * 管理员名已存在
     */
    ADMIN_USERNAME_EXIST("ADMIN_USERNAME_EXIST","管理员名已存在"),
    /**
     * 管理员修改管理员名失败
     */
    ADMIN_MODIFY_NICKNAME_FAIL("ADMIN_MODIFY_NICKNAME_FAIL","修改管理员名失败"),
    /**
     * 管理员修改密码失败
     */
    ADMIN_MODIFY_PASSWORD_FAIL("ADMIN_MODIFY_PASSWORD_FAIL","修改密码失败"),
    /**
     * 管理员状态异常
     */
    ADMIN_STATUS_ERROR("ADMIN_STATUS_ERROR","管理员状态错误"),

    /**
     * 管理员操作失败
     */
    ADMIN_OPERATE_FAILED("ADMIN_OPERATE_FAILED", "数据库操作异常，管理员操作失败"),

    /**
     * 管理员密码不一致
     */
    ADMIN_PASSWORD_NOT_MATCH("ADMIN_PASSWORD_NOT_MATCH", "两次输入的密码不一致"),

    /**
     * 管理员密码重复
     */
    ADMIN_PASSWORD_DUPLICATE("ADMIN_PASSWORD_DUPLICATE", "新密码与旧密码重复"),

    /**
     * 管理员创建失败
     */
    ADMIN_CREATE_FAIL("ADMIN_CREATE_FAIL", "管理员创建失败"),

    /**
     * 管理员更新状态失败
     */
    ADMIN_UPDATE_STATUS_FAIL("ADMIN_UPDATE_STATUS_FAIL", "管理员更新状态失败"),

    /**
     * 管理员重置密码失败
     */
    ADMIN_RESET_PASSWORD_FAIL("ADMIN_RESET_PASSWORD_FAIL", "管理员重置密码失败"),

    /**
     * 管理员密码校验失败
     */
    ADMIN_PASSWD_CHECK_FAIL("ADMIN_PASSWD_CHECK_FAIL", "密码错误");

    private final String code;

    private final String message;

    AdminErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}

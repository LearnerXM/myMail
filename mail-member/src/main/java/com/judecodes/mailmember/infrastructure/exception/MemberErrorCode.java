package com.judecodes.mailmember.infrastructure.exception;


import com.judecodes.mailbase.exception.ErrorCode;

/**
 * 用户错误码
 *
 * @author hollis
 */
public enum MemberErrorCode implements ErrorCode {
    /**
     * 重复电话号码
     */
    DUPLICATE_TELEPHONE_NUMBER("DUPLICATE_TELEPHONE_NUMBER", "重复电话号码"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST("USER_NOT_EXIST", "用户不存在"),

    /**
     * 用户状态不能进行操作
     */
    USER_STATUS_CANT_OPERATE("USER_STATUS_CANT_OPERATE", "用户状态不能进行操作"),


    /**
     * 用户操作失败
     */
    USER_OPERATE_FAILED("USER_OPERATE_FAILED", "数据库操作异常，用户注册失败"),

    /**
     * 用户密码校验失败
     */
    USER_PASSWD_CHECK_FAIL("USER_PASSWD_CHECK_FAIL", "用户密码校验失败"),
    /**
     * 用户查询失败
     */
    USER_QUERY_FAIL("USER_QUERY_FAIL", "用户查询失败"),
    /**
     * 用户名已存在
     */
    NICK_NAME_IS_NULL("NICK_NAME_IS_NULL", "用户名不能为空"),
    /**
     * 验证码错误
     */
    CODE_ERROR("CODE_ERROR", "验证码错误"),

    /**
     * Token异常
     */
    TOKEN_ERROR("TOKEN_ERROR","密码Token异常"),
    /**
     * 用户上传图片失败
     */
    USER_UPLOAD_PICTURE_FAIL("USER_UPLOAD_PICTURE_FAIL", "用户上传图片失败"),
    /**
     * 用户修改用户名失败
     */
    USER_MODIFY_NICKNAME_FAIL("USER_MODIFY_NICKNAME_FAIL","修改用户名失败"),
    /**
     * 用户修改密码失败
     */
    USER_MODIFY_PASSWORD_FAIL("USER_MODIFY_PASSWORD_FAIL","修改密码失败"),

    /**
     * 用户修改个性签名失败
     */
    USER_MODIFY_SIGNATURE_FAIL("USER_MODIFY_SIGNATURE_FAIL","修改个性签名失败"),
    /**
     * 非法手机号
     */
    ILLEGAL_PHONE("ILLEGAL_PHONE","非法手机号"),
    /**
     * 用户状态异常
     */
    USER_STATUS_ERROR("USER_STATUS_ERROR","用户状态异常"),
    ;

    private final String code;

    private final String message;

    MemberErrorCode(String code, String message) {
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

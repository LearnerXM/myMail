package com.judecodes.mailmember.infrastructure.exception;


import com.judecodes.mailbase.exception.BizException;
import com.judecodes.mailbase.exception.ErrorCode;

/**
 * 用户异常
 *
 * @author Hollis
 */
public class MemberException extends BizException {

    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public MemberException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public MemberException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public MemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }

}

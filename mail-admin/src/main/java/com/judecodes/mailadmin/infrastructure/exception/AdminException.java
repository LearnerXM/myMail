package com.judecodes.mailadmin.infrastructure.exception;

import com.judecodes.mailbase.exception.BizException;
import com.judecodes.mailbase.exception.ErrorCode;

public class AdminException extends BizException {
    public AdminException(ErrorCode errorCode) {
        super(errorCode);
    }
}

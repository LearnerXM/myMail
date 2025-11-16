package com.judecodes.mailweb.handle;

import com.judecodes.mailbase.exception.BizException;
import com.judecodes.mailbase.exception.SystemException;
import com.judecodes.mailweb.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.judecodes.mailbase.response.ResponseCode.SYSTEM_ERROR;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常
     */
    @ExceptionHandler(BizException.class)
    public Result<Void> handleBizException(BizException bizException) {
        log.error("bizException occurred.", bizException);
        Result result = new Result();
        result.setCode(bizException.getErrorCode().getCode());
        if (bizException.getMessage() == null) {
            result.setMessage(bizException.getErrorCode().getMessage());
        } else {
            result.setMessage(bizException.getMessage());
        }
        result.setSuccess(false);
        return result;
    }

    /**
     * 自定义系统异常处理器
     *
     * @param systemException
     * @return
     */
    @ExceptionHandler(SystemException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result systemExceptionHandler(SystemException systemException) {
        log.error("systemException occurred.", systemException);
        Result result = new Result();
        result.setCode(systemException.getErrorCode().getCode());
        if (systemException.getMessage() == null) {
            result.setMessage(systemException.getErrorCode().getMessage());
        } else {
            result.setMessage(systemException.getMessage());
        }
        result.setSuccess(false);
        return result;
    }
    /**
     * 兜底异常处理
     */
    @ExceptionHandler(Throwable.class)
    public Result<Void> handleThrowable(Throwable throwable) {
        log.error("throwable occurred.",throwable);
        Result result = new Result();
        result.setCode(SYSTEM_ERROR.name());
        result.setMessage("哎呀，当前网络比较拥挤，请您稍后再试~");
        result.setSuccess(false);
        return result;
    }
}

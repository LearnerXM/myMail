package com.judecodes.mailweb.handle;

import com.google.common.collect.Maps;
import com.judecodes.mailbase.exception.BizErrorCode;
import com.judecodes.mailbase.exception.BizException;
import com.judecodes.mailbase.exception.SystemException;
import com.judecodes.mailweb.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Map;

import static com.judecodes.mailbase.response.ResponseCode.SYSTEM_ERROR;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 自定义方法参数校验异常处理器
     *
     * @param e
     * @return R
     */
    // 1. 先处理参数校验失败（包含你的 @Phone）
    @ExceptionHandler(HandlerMethodValidationException.class)
    public Result handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        // 从异常里把所有校验错误消息拿出来（取第一个就行）
        String msg = e.getAllValidationResults().stream()
                .flatMap(result -> result.getResolvableErrors().stream())
                .map(MessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("参数校验失败");
        // 这里可以打 INFO/WARN，而不是 ERROR，看你喜好
        log.warn("参数校验失败: {}", msg);
        return Result.error(BizErrorCode.PHONE_VALIDATION_FAILED.getCode(), msg);
    }

    /**
     * 自定义方法参数校验异常处理器
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException occurred.", ex);
        Map<String, String> errors = Maps.newHashMapWithExpectedSize(1);
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
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

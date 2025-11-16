package com.judecodes.mailweb.vo;


import lombok.Data;



import static com.judecodes.mailbase.response.ResponseCode.SUCCESS;

@Data
public class Result<T>  {


    /** 业务状态码*/
    private String code;

    /**
     * 是否成功
     */
    private Boolean success;

    /** 提示信息 */
    private String message;

    /** 结果数据 */
    private T data;

    public Result() {
    }

    public Result(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    public Result(Boolean success, String code, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = code;
    }


    // ========= 静态构造方法 =========
    public static <T> Result<T> success(T data) {
        return new Result<>(true, SUCCESS.name(), SUCCESS.name(), data);
    }

    public static <T> Result<T> error(String errorCode,String errorMsg) {
        return new Result<>(false, errorCode, errorMsg, null);
    }


}

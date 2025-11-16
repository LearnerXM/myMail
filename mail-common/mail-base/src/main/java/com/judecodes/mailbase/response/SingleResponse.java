package com.judecodes.mailbase.response;

import lombok.Data;

@Data
public class SingleResponse<T> extends BaseResponse {

    private T data;

    public static <T> SingleResponse<T> of(T data) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static <T> SingleResponse<T> fail(String errorCode,String errorMessage){
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(false);
        response.setResponseCode(errorCode);
        response.setResponseMessage(errorMessage);
        return response;
    }


    private static final long serialVersionUID = 1L;
}

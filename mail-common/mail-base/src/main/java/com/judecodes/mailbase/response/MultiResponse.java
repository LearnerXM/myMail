package com.judecodes.mailbase.response;

import lombok.Data;

import java.util.List;

@Data
public class MultiResponse<T> extends BaseResponse{

    private List<T> datas;

    public static <T> MultiResponse<T> success(List<T> data){
        MultiResponse<T> response = new MultiResponse<>();
        response.setSuccess(true);
        response.setDatas(data);
        return response;
    }


    private static final long serialVersionUID = 1L;
}

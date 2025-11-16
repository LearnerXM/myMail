package com.judecodes.mailbase.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {

    private Boolean success;

    private String responseCode;

    private String responseMessage;

    private static final long serialVersionUID = 1L;

}

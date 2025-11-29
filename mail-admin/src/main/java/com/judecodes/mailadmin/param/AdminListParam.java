package com.judecodes.mailadmin.param;


import com.judecodes.mailbase.request.PageRequest;
import lombok.Data;

@Data
public class AdminListParam extends PageRequest {

    private String state;

    private String keyWord;
}

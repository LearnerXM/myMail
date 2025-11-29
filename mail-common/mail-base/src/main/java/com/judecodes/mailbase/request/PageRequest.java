package com.judecodes.mailbase.request;

import lombok.Data;

@Data
public class PageRequest extends BaseRequest {
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 每页结果数
     */
    private int pageSize;

    private static final long serialVersionUID = 1L;
}

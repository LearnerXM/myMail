package com.judecodes.mailbase.response;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponse<T>  extends MultiResponse<T>{

    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 每页结果数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 总数
     */
    private int total;

    public static <T> PageResponse<T> of(List<T> data, int currentPage, int pageSize, int total){
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setDatas(data);
        response.setCurrentPage(currentPage);
        response.setPageSize(pageSize);
        response.setTotalPage((total + pageSize - 1) / pageSize);
        response.setTotal(total);
        return response;

    }


    private static final long serialVersionUID = 1L;
}

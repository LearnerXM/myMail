package com.judecodes.mailweb.vo;

import com.judecodes.mailbase.response.PageResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import static com.judecodes.mailbase.response.ResponseCode.SUCCESS;

/**
 * 多结果返回（列表/分页）
 */
@Data
public class PageResult<T> extends Result<List<T>>  {

    /**
     * 总记录数
     */
    private long total;
    /**
     * 当前页码
     */
    private int page;
    /**
     * 每页记录数
     */
    private int size;

    public PageResult() {
        super();
    }

    public PageResult(Boolean success, String code, String message, List<T> data, long total, int page, int size) {
        super(success, code, message, data);
        this.total = total;
        this.page = page;
        this.size = size;
    }

    public static <T> PageResult<T> success(PageResponse<T> pageResponse) {
        return new PageResult<>(true, SUCCESS.name(), SUCCESS.name(), pageResponse.getDatas(), pageResponse.getTotal(), pageResponse.getCurrentPage(), pageResponse.getPageSize());

    }

    public static <T> PageResult<T> successMulti(List<T> data, long total, int page, int size) {
        return new PageResult<>(true, SUCCESS.name(), SUCCESS.name(), data, total, page, size);
    }

    public static <T> PageResult<T> errorMulti(String errorCode, String errorMsg) {
        return new PageResult<>(true, errorCode, errorMsg, null, 0, 0, 0);
    }
}

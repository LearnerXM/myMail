package com.judecodes.mailadmin.vo;


import java.time.LocalDateTime;

public class ResourceInfo {

    private Long id;

    /**
     * 创建时间
     */

    private LocalDateTime createTime;

    /**
     * 资源名称
     */

    private String name;

    /**
     * 资源URL
     */

    private String url;

    /**
     * 描述
     */

    private String description;

    /**
     * 资源分类ID
     */

    private Long categoryId;

    /**
     * 更新时间
     */

    private LocalDateTime updateTime;


}

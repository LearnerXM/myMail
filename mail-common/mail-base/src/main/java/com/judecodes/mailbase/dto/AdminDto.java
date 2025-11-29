package com.judecodes.mailbase.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdminDto {
    private Long id;
    private String username;
    private List<String> roleList;
}

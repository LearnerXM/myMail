package com.judecodes.mailadmin.controller;

import com.judecodes.mailadmin.domain.entity.Admin;
import com.judecodes.mailadmin.domain.service.AdminService;
import com.judecodes.mailadmin.param.AdminLoginParam;
import com.judecodes.mailadmin.vo.AdminLoginVO;
import com.judecodes.mailweb.vo.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author judecodes
 * @since 2025-11-26
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Result<AdminLoginVO> login(@Valid @RequestBody AdminLoginParam adminLoginParam) {
        Admin admin=adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword());
        return null;
    }

}

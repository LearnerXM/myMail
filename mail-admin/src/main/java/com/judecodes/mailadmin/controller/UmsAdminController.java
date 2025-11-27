package com.judecodes.mailadmin.controller;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;

import com.judecodes.mailadmin.domain.entity.Admin;
import com.judecodes.mailadmin.domain.service.AdminService;
import com.judecodes.mailadmin.infrastructure.exception.AdminErrorCode;
import com.judecodes.mailadmin.infrastructure.exception.AdminException;
import com.judecodes.mailadmin.param.AdminLoginParam;

import com.judecodes.mailadmin.vo.AdminLoginVO;
import com.judecodes.mailweb.vo.Result;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


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
public class UmsAdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 默认登录超时时间：7天
     */
    private static final Integer DEFAULT_LOGIN_SESSION_TIMEOUT = 60 * 60 * 24 * 7;
    @PostMapping("/login")
    public Result<AdminLoginVO> login(@Valid @RequestBody AdminLoginParam adminLoginParam) {
        Admin admin=adminService.findByUsernameAndPassword(adminLoginParam.getUsername(),adminLoginParam.getPassword());

        if (admin == null) {
            throw new AdminException(AdminErrorCode.ADMIN_NOT_EXIST);
        }
        StpUtil.login(admin.getId(),new SaLoginModel().setIsLastingCookie(adminLoginParam.getRememberMe())
                .setTimeout(DEFAULT_LOGIN_SESSION_TIMEOUT));
        //TODO  获取权限和角色列表存到session
        StpUtil.getSession().set(admin.getId().toString(),admin);

        AdminLoginVO adminLoginVO = new AdminLoginVO(admin);
        return Result.success(adminLoginVO);
    }

//    @Operation(summary = "用户注册")
//    @PostMapping(value = "/register")
//    public CommonResult<UmsAdmin> register(@Validated @RequestBody AdminParam adminParam) {
//        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
//        if (umsAdmin == null) {
//            return CommonResult.failed();
//        }
//        return CommonResult.success(umsAdmin);
//    }


}

package com.judecodes.mailadmin.controller;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;

import cn.hutool.core.bean.BeanUtil;
import com.judecodes.mailadmin.domain.entity.Admin;
import com.judecodes.mailadmin.domain.service.AdminService;
import com.judecodes.mailadmin.infrastructure.exception.AdminErrorCode;
import com.judecodes.mailadmin.infrastructure.exception.AdminException;
import com.judecodes.mailadmin.param.*;


import com.judecodes.mailadmin.vo.*;
import com.judecodes.mailbase.constant.AuthConstant;
import com.judecodes.mailbase.constant.UserType;
import com.judecodes.mailbase.dto.AdminDto;
import com.judecodes.mailweb.vo.Result;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        Admin admin = adminService.getByUsernameAndPassword(adminLoginParam.getUsername(), adminLoginParam.getPassword());
        List<String> roleNameList = adminService.getRoleNameListByAdminId(admin.getId());
        StpUtil.login(admin.getId(), new SaLoginModel().setIsLastingCookie(adminLoginParam.getRememberMe())
                .setTimeout(DEFAULT_LOGIN_SESSION_TIMEOUT));
        //构建Stp存储的管理员信息
        AdminDto adminDto = new AdminDto();
        adminDto.setId(admin.getId());
        adminDto.setUsername(admin.getUsername());
        adminDto.setRoleList(roleNameList);

        StpUtil.getSession().set(AuthConstant.ADMIN_ROLE_INFO, adminDto);
        StpUtil.getSession().set(AuthConstant.STP_IDENTITY_TYPE, UserType.ADMIN);

        AdminLoginVO adminLoginVO = new AdminLoginVO(admin);
        return Result.success(adminLoginVO);
    }

    @GetMapping("/info")
    public Result<AdminInfoVO> getInfo() {
        String loginId = (String) StpUtil.getLoginId();
        Admin admin = adminService.findById(Long.parseLong(loginId));
        List<String> roleNameList = adminService.getRoleNameListByAdminId(Long.parseLong(loginId));
        List<String> permissionNameList = adminService.getPermissionNameListByAdminId(Long.parseLong(loginId));
        //TODO 每个管理员的菜单列表
        AdminInfoVO adminInfo = new AdminInfoVO();
        BeanUtil.copyProperties(admin, adminInfo);
        adminInfo.setRoleNamesList(roleNameList);
        adminInfo.setPermissionNameList(permissionNameList);
        return Result.success(adminInfo);
    }


    @PostMapping("/logout")
    public Result<Boolean> logout() {
        StpUtil.logout();
        return Result.success(true);
    }

    @PostMapping("/modifyPassword")
    public Result<Boolean> modifyPassword(@Valid @RequestBody AdminModifyPasswordParam adminModifyPasswordParam) {

        String loginId = (String) StpUtil.getLoginId();
        String oldPassword = adminModifyPasswordParam.getOldPassword();
        String newPassword = adminModifyPasswordParam.getNewPassword();
        String checkPassword = adminModifyPasswordParam.getCheckPassword();

        if (!newPassword.equals(checkPassword)) {
            throw new AdminException(AdminErrorCode.ADMIN_PASSWORD_NOT_MATCH);
        }

        adminService.modifyPassword(loginId, oldPassword, newPassword);

        StpUtil.logout();
        return Result.success(true);
    }



}

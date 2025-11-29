package com.judecodes.mailadmin.controller;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.judecodes.mailadmin.constant.AdminStateEnum;
import com.judecodes.mailadmin.domain.entity.Admin;
import com.judecodes.mailadmin.domain.service.AdminService;
import com.judecodes.mailadmin.infrastructure.exception.AdminErrorCode;
import com.judecodes.mailadmin.infrastructure.exception.AdminException;
import com.judecodes.mailadmin.param.*;


import com.judecodes.mailadmin.vo.*;
import com.judecodes.mailbase.constant.AuthConstant;
import com.judecodes.mailbase.constant.UserType;
import com.judecodes.mailbase.dto.AdminDto;
import com.judecodes.mailbase.exception.BizErrorCode;
import com.judecodes.mailbase.exception.BizException;
import com.judecodes.mailbase.response.PageResponse;
import com.judecodes.mailweb.vo.PageResult;
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

    //待测试
    @PostMapping("/createAdmin")
    public Result<Boolean> createAdmin(@Valid @RequestBody CreateAdminParam createAdminParam) {
        Admin admin = new Admin();
        BeanUtil.copyProperties(createAdminParam, admin);
        adminService.createAdmin(admin);
        return Result.success(true);
    }

    @PostMapping("/updateStatus/{id}")
    public Result<Boolean> updateStatus( @PathVariable Long id, @RequestParam Integer status) {
        if (status != AdminStateEnum.ENABLED.getCode() && status != AdminStateEnum.DISABLED.getCode()) {
            throw new AdminException(AdminErrorCode.ADMIN_STATUS_ERROR);
        }
        adminService.updateStatus(id, status);
        return Result.success(true);
    }

    @PostMapping("/resetPassword/{id}")
    public Result<Boolean> resetPassword( @PathVariable Long id) {
        if(ObjectUtil.isEmpty(id)){
            throw new BizException(BizErrorCode.PARAMETER_ERROR);
        }
        adminService.resetPassword(id);
        return Result.success(true);
    }

    //TODO 分页查询，待补充
    @GetMapping("/list")
    public PageResult<AdminBasicInfoVO> getAdminList(@RequestBody AdminListParam adminListParam) {
        PageResponse<AdminBasicInfoVO> adminPageResponse = adminService.pageQueryByState(adminListParam.getKeyWord(),adminListParam.getState(), adminListParam.getCurrentPage(), adminListParam.getPageSize());

        return PageResult.success(adminPageResponse);
    }

    @GetMapping("/getAdminBasicInfo/{id}")
    public Result<AdminBasicInfoVO> getAdminBasicInfoById(@PathVariable Long id) {
        if(ObjectUtil.isEmpty(id)){
            throw new BizException(BizErrorCode.PARAMETER_ERROR);
        }
        Admin admin = adminService.findById(id);
        AdminBasicInfoVO adminBasicInfo = new AdminBasicInfoVO();
        BeanUtil.copyProperties(admin, adminBasicInfo);
        return Result.success(adminBasicInfo);
    }

    @GetMapping("/getAdminInfo/{id}")
    public Result<AdminInfoVO> getAdminById(@PathVariable Long id) {
        if(ObjectUtil.isEmpty(id)){
            throw new BizException(BizErrorCode.PARAMETER_ERROR);
        }
        Admin admin = adminService.findById(id);
        List<String> roleNameList = adminService.getRoleNameListByAdminId(id);
        List<String> permissionNameList = adminService.getPermissionNameListByAdminId(id);
        AdminInfoVO adminInfo = new AdminInfoVO();
        BeanUtil.copyProperties(admin, adminInfo);
        adminInfo.setRoleNamesList(roleNameList);
        adminInfo.setPermissionNameList(permissionNameList);
        return Result.success(adminInfo);
    }

    /* 1. 给管理员分配角色
     * POST /admin/role/update
     */
    @PostMapping("/role/update")
//    @SaCheckRole("SUPER_ADMIN")   // 一般只有超级管理员能分配角色，按你自己权限设计调整
    public Result<Boolean> updateAdminRole(@RequestBody @Valid AdminRoleUpdateParam request) {
        adminService.updateAdminRole(request.getAdminId(), request.getRoleIdList());
        return Result.success(true);
    }

    /**
     * 2. 查询管理员已有角色
     * GET /admin/role/{adminId}
     */
    @GetMapping("/role/{adminId}")
//    @SaCheckRole("SUPER_ADMIN")   // 或者：只允许自己查询自己，再看业务
    public Result<List<RoleBasicInfoVO>> getAdminRoleList(@PathVariable Long adminId) {
        List<RoleBasicInfoVO> roleList = adminService.getRoleListByAdminId(adminId);
        return Result.success(roleList);
    }

    /**
     * 3. 查询管理员拥有的资源/权限
     * GET /admin/resource/{adminId}
     */
//    @GetMapping("/resource/{adminId}")
//    @SaCheckRole("SUPER_ADMIN")   // 同上，看你如何设计权限
//    public Result<List<ResourceBasicInfo>> getAdminResourceList(@PathVariable Long adminId) {
//        List<ResourceBasicInfo> resourceList = adminService.getResourceListByAdminId(adminId);
//        return Result.success(resourceList);
//    }

}

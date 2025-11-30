package com.judecodes.mailadmin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.judecodes.mailadmin.constant.AdminStateEnum;
import com.judecodes.mailadmin.domain.entity.Admin;
import com.judecodes.mailadmin.domain.service.AdminService;
import com.judecodes.mailadmin.infrastructure.exception.AdminErrorCode;
import com.judecodes.mailadmin.infrastructure.exception.AdminException;
import com.judecodes.mailadmin.param.AdminRoleUpdateParam;
import com.judecodes.mailadmin.param.CreateAdminParam;
import com.judecodes.mailadmin.vo.AdminBasicInfoVO;
import com.judecodes.mailadmin.vo.AdminInfoVO;
import com.judecodes.mailadmin.vo.RoleBasicInfoVO;
import com.judecodes.mailbase.exception.BizErrorCode;
import com.judecodes.mailbase.exception.BizException;
import com.judecodes.mailbase.response.PageResponse;
import com.judecodes.mailweb.vo.PageResult;
import com.judecodes.mailweb.vo.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/superadmin")
public class UmsSuperAdminController {
    @Autowired
    private AdminService adminService;


    @PostMapping("/createAdmin")

    public Result<Boolean> createAdmin(@Valid @RequestBody CreateAdminParam createAdminParam) {
        Admin admin = new Admin();
        BeanUtil.copyProperties(createAdminParam, admin);
        adminService.createAdmin(admin);
        return Result.success(true);
    }

    @PostMapping("/updateStatus/{id}")
    public Result<Boolean> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        if (status != AdminStateEnum.ENABLED.getCode() && status != AdminStateEnum.DISABLED.getCode()) {
            throw new AdminException(AdminErrorCode.ADMIN_STATUS_ERROR);
        }
        adminService.updateStatus(id, status);
        return Result.success(true);
    }

    @PostMapping("/resetPassword/{id}")
    public Result<Boolean> resetPassword(@PathVariable Long id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new BizException(BizErrorCode.PARAMETER_ERROR);
        }
        adminService.resetPassword(id);
        return Result.success(true);
    }


    @GetMapping("/list")
    public PageResult<AdminBasicInfoVO> getAdminList(String username,String nickName,@NotBlank String state,  int currentPage, int pageSize) {
        PageResponse<Admin> adminPageResponse = adminService.pageQueryByState(username, nickName,state, currentPage,  pageSize);
        //列表转换
        if (adminPageResponse.getDatas()==null){
            return PageResult.errorMulti(AdminErrorCode.ADMIN_NOT_EXIST.getCode(), "没有找到相关管理员");
        }
        List<AdminBasicInfoVO> adminBasicInfoVOList = adminPageResponse.getDatas().stream()
                .map(admin -> {
                    AdminBasicInfoVO adminBasicInfoVO = new AdminBasicInfoVO();
                    BeanUtil.copyProperties(admin, adminBasicInfoVO);
                    return adminBasicInfoVO;
                }).toList();


        return PageResult.successMulti(adminBasicInfoVOList, adminPageResponse.getTotal(), adminPageResponse.getCurrentPage(), adminPageResponse.getPageSize());
    }

    @GetMapping("/getAdminBasicInfo/{id}")
    public Result<AdminBasicInfoVO> getAdminBasicInfoById(@PathVariable Long id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new BizException(BizErrorCode.PARAMETER_ERROR);
        }
        Admin admin = adminService.findById(id);
        AdminBasicInfoVO adminBasicInfo = new AdminBasicInfoVO();
        BeanUtil.copyProperties(admin, adminBasicInfo);
        return Result.success(adminBasicInfo);
    }

    @GetMapping("/getAdminInfo/{id}")
    public Result<AdminInfoVO> getAdminById(@PathVariable Long id) {
        if (ObjectUtil.isEmpty(id)) {
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

    //TODO 查询管理员拥有的资源/权限
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

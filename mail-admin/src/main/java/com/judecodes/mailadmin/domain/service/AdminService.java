package com.judecodes.mailadmin.domain.service;

import com.judecodes.mailadmin.domain.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

import com.judecodes.mailadmin.vo.AdminBasicInfoVO;
import com.judecodes.mailadmin.vo.ResourceBasicInfoVO;

import com.judecodes.mailadmin.vo.RoleBasicInfoVO;
import com.judecodes.mailbase.response.PageResponse;


import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author judecodes
 * @since 2025-11-26
 */
public interface AdminService extends IService<Admin> {

    Admin findById(Long id);
    Admin getByUsernameAndPassword(String username, String password);

    void modifyPassword(String loginId, String oldPassword, String newPassword);

    //测试接口
    public void modifyPasswordTest(String name, String newPassword);


    List<String> getRoleNameListByAdminId(Long adminId);

    List<String> getPermissionNameListByAdminId(Long adminId);

    void createAdmin(Admin admin);

    void updateStatus(Long id, Integer status);

    void resetPassword(Long id);

    PageResponse<AdminBasicInfoVO> pageQueryByState(String keyWord, String state, int currentPage, int pageSize);

    // 分配角色
    void updateAdminRole(Long adminId, List<Long> roleIdList);

    // 查询管理员已有角色
    List<RoleBasicInfoVO> getRoleListByAdminId(Long adminId);

    // 查询管理员拥有的资源/权限
    List<ResourceBasicInfoVO> getResourceListByAdminId(Long adminId);

}

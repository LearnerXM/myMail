package com.judecodes.mailadmin.domain.service;

import com.judecodes.mailadmin.domain.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

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

    Admin getByUsernameAndPassword(String username, String password);

    List<String> getRoleNameListByAdminId(Long adminId);

    List<String> getPermissionNameListByAdminId(Long adminId);
}

package com.judecodes.mailadmin.domain.service;

import com.judecodes.mailadmin.domain.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author judecodes
 * @since 2025-11-26
 */
public interface AdminService extends IService<Admin> {

    Admin login(String username, String password);

}

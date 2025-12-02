package com.judecodes.mailadmin.domain.service;

import com.judecodes.mailadmin.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务类
 * </p>
 *
 * @author judecodes
 * @since 2025-11-28
 */
public interface RoleService extends IService<Role> {

    void createRole(Role role);

    void updateRole(Role role);

    void deleteRole(Long id);

    void deleteBatch(List<Long> ids);

}

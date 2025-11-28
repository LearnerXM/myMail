package com.judecodes.mailadmin.domain.service.impl;

import com.judecodes.mailadmin.domain.entity.Role;
import com.judecodes.mailadmin.infrastructure.mapper.RoleMapper;
import com.judecodes.mailadmin.domain.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户角色表 服务实现类
 * </p>
 *
 * @author judecodes
 * @since 2025-11-28
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}

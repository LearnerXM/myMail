package com.judecodes.mailadmin.domain.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.judecodes.mailadmin.domain.entity.AdminRoleRelation;
import com.judecodes.mailadmin.domain.entity.Role;
import com.judecodes.mailadmin.domain.entity.RoleMenuRelation;
import com.judecodes.mailadmin.domain.entity.RoleResourceRelation;
import com.judecodes.mailadmin.domain.service.AdminRoleRelationService;
import com.judecodes.mailadmin.domain.service.RoleMenuRelationService;
import com.judecodes.mailadmin.domain.service.RoleResourceRelationService;
import com.judecodes.mailadmin.infrastructure.exception.AdminErrorCode;
import com.judecodes.mailadmin.infrastructure.exception.AdminException;
import com.judecodes.mailadmin.infrastructure.mapper.RoleMapper;
import com.judecodes.mailadmin.domain.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private AdminRoleRelationService adminRoleRelationService;

    @Autowired
    private RoleMenuRelationService roleMenuRelationService;

    @Autowired
    private RoleResourceRelationService roleResourceRelationService;

    @Override
    public void createRole(Role role) {
        String name = role.getName();

        Role excitedRole = this.lambdaQuery()
                .eq(StringUtils.isNotBlank(name), Role::getName, name)
                .one();

        if (excitedRole != null) {
            throw new AdminException(AdminErrorCode.ROLE_EXIST);
        }

        boolean result = this.save(role);
        if (!result) {
            throw new AdminException(AdminErrorCode.ROLE_CREATE_FAIL);
        }

    }

    @Override
    public void updateRole(Role role) {
        Long id = role.getId();
        Role excitedRole = this.lambdaQuery()
                .eq(Role::getId, id)
                .one();
        if (excitedRole == null) {
            throw new AdminException(AdminErrorCode.ROLE_NOT_EXIST);
        }
        boolean result = this.updateById(role);
        if (!result)
            throw new AdminException(AdminErrorCode.ROLE_UPDATE_FAIL);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        Role exitedRole = this.getById(id);
        if (exitedRole == null) {
            throw new AdminException(AdminErrorCode.ROLE_NOT_EXIST);
        }

        boolean exists = adminRoleRelationService.lambdaQuery()
                .eq(AdminRoleRelation::getRoleId, id)
                .exists();
        if (!exists) {
            throw new AdminException(AdminErrorCode.ADMIN_ROLE_RELATION_EXIST);
        }
        boolean removeByIdResult = this.removeById(id);
        if (!removeByIdResult) {
            throw new AdminException(AdminErrorCode.ROLE_DELETE_FAIL);
        }
        LambdaQueryChainWrapper<RoleMenuRelation> roleMenuRelationLambdaQueryChainWrapper = roleMenuRelationService.lambdaQuery()
                .eq(RoleMenuRelation::getRoleId, id);

        roleMenuRelationService.remove(roleMenuRelationLambdaQueryChainWrapper);

        LambdaQueryChainWrapper<RoleResourceRelation> roleResourceRelationLambdaQueryChainWrapper = roleResourceRelationService.lambdaQuery()
                .eq(RoleResourceRelation::getRoleId, id);
        roleResourceRelationService.remove(roleResourceRelationLambdaQueryChainWrapper);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> ids) {

        List<Long> distinctList = ids.stream().distinct().toList();

        if (CollUtil.isEmpty(distinctList)) {
            throw new AdminException(AdminErrorCode.ROLE_NOT_EXIST);
        }

        List<Role> exitedRoleList = this.lambdaQuery()
                .in(Role::getId, distinctList)
                .list();
        if (CollUtil.isEmpty(exitedRoleList)) {
            throw new AdminException(AdminErrorCode.ROLE_NOT_EXIST);
        }
        List<Long> exitedList = exitedRoleList.stream().map(Role::getId).toList();

        boolean exists = adminRoleRelationService.lambdaQuery()
                .in(AdminRoleRelation::getRoleId, exitedList)
                .exists();
        if (!exists) {
            throw new AdminException(AdminErrorCode.ADMIN_ROLE_RELATION_EXIST);
        }


        boolean removeByIdsResult = this.removeByIds(exitedList);
        if (!removeByIdsResult) {
            throw new AdminException(AdminErrorCode.ROLE_DELETE_FAIL);
        }
        LambdaQueryChainWrapper<RoleMenuRelation> roleMenuRelationLambdaQueryChainWrapper = roleMenuRelationService.lambdaQuery()
                .in(RoleMenuRelation::getRoleId, exitedList);

        roleMenuRelationService.remove(roleMenuRelationLambdaQueryChainWrapper);

        LambdaQueryChainWrapper<RoleResourceRelation> roleResourceRelationLambdaQueryChainWrapper = roleResourceRelationService.lambdaQuery()
                .in(RoleResourceRelation::getRoleId, exitedList);
        roleResourceRelationService.remove(roleResourceRelationLambdaQueryChainWrapper);


    }
}

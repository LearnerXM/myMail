package com.judecodes.mailadmin.domain.service.impl;


import cn.hutool.core.collection.CollUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.judecodes.mailadmin.constant.AdminStateEnum;
import com.judecodes.mailadmin.domain.entity.Admin;
import com.judecodes.mailadmin.domain.entity.AdminRoleRelation;
import com.judecodes.mailadmin.domain.service.AdminRoleRelationService;
import com.judecodes.mailadmin.infrastructure.exception.AdminErrorCode;
import com.judecodes.mailadmin.infrastructure.exception.AdminException;
import com.judecodes.mailadmin.infrastructure.mapper.AdminMapper;
import com.judecodes.mailadmin.domain.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.judecodes.mailadmin.infrastructure.mapper.ResourceMapper;
import com.judecodes.mailadmin.infrastructure.mapper.RoleMapper;


import com.judecodes.mailadmin.vo.ResourceBasicInfoVO;

import com.judecodes.mailadmin.vo.RoleBasicInfoVO;

import com.judecodes.mailbase.response.PageResponse;
import com.judecodes.maildatasource.utils.PasswordUtils;
import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author judecodes
 * @since 2025-11-26
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private AdminRoleRelationService adminRoleRelationService;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private ResourceMapper resourceMapper;

    @Override
    public Admin findById(Long id) {
        Admin admin = this.getById(id);
        checkAdmin(admin);
        return admin;
    }

    @Override
    public Admin getByUsernameAndPassword(String username, String password) {
        Admin admin = this.lambdaQuery()
                .eq(Admin::getUsername, username)
                .one();
        checkAdmin(admin);
        if (!PasswordUtils.matches(password, admin.getPassword())) {
            throw new AdminException(AdminErrorCode.ADMIN_PASSWD_CHECK_FAIL);
        }
        if (admin.getStatus() == AdminStateEnum.DISABLED.getCode()) {
            throw new AdminException(AdminErrorCode.ADMIN_STATUS_ERROR);
        }
        return admin;
    }

    private void checkAdmin(Admin admin) {
        if(admin == null){
            throw new AdminException(AdminErrorCode.ADMIN_NOT_EXIST);
        }
        if (admin.getStatus()!= AdminStateEnum.ENABLED.getCode()){
            throw new AdminException(AdminErrorCode.ADMIN_STATUS_ERROR);
        }
    }

    @Override
    public void modifyPassword(String loginId, String oldPassword, String newPassword) {
        Admin admin = this.getById(Long.parseLong(loginId));
        checkAdmin(admin);
        if (!PasswordUtils.matches(oldPassword, admin.getPassword())) {
            throw new AdminException(AdminErrorCode.ADMIN_PASSWD_CHECK_FAIL);
        }
        if (PasswordUtils.matches(newPassword, admin.getPassword())) {
            throw new AdminException(AdminErrorCode.ADMIN_PASSWORD_DUPLICATE);
        }
        admin.setPassword(PasswordUtils.encode(newPassword));
        boolean result = this.updateById(admin);
        if (!result) {
            throw new AdminException(AdminErrorCode.ADMIN_MODIFY_PASSWORD_FAIL);
        }

    }

    public void modifyPasswordTest(String name, String newPassword) {
        Admin admin = this.lambdaQuery()
                .eq(Admin::getUsername, name)
                .one();
        checkAdmin(admin);
        admin.setPassword(PasswordUtils.encode(newPassword));
        boolean result = this.updateById(admin);
        if (!result)
            throw new AdminException(AdminErrorCode.ADMIN_MODIFY_PASSWORD_FAIL);

    }

    @Override
    public void createAdmin(Admin admin) {

        String username = admin.getUsername();
        //TODO selectOne底层查询的是列表，可能存在性能问题
        Admin excitedAdmin = this.lambdaQuery()
                .eq(Admin::getUsername, username)
                .one();
        if (excitedAdmin != null) {
            throw new AdminException(AdminErrorCode.ADMIN_USERNAME_EXIST);
        }
        admin.setPassword(PasswordUtils.encode(admin.getPassword()));
        boolean result = this.save(admin);
        if (!result) {
            throw new AdminException(AdminErrorCode.ADMIN_CREATE_FAIL);
        }
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Admin admin = this.getById(id);
        if(admin == null){
            throw new AdminException(AdminErrorCode.ADMIN_NOT_EXIST);
        }
        admin.setStatus(status);
        boolean result = this.updateById(admin);
        if (!result) {
            throw new AdminException(AdminErrorCode.ADMIN_UPDATE_STATUS_FAIL);
        }
    }

    @Override
    public void resetPassword(Long id) {
        Admin admin = this.getById(id);
        if(admin == null){
            throw new AdminException(AdminErrorCode.ADMIN_NOT_EXIST);
        }
        admin.setPassword(PasswordUtils.encode("admin123"));
        boolean result = this.updateById(admin);
        if (!result) {
            throw new AdminException(AdminErrorCode.ADMIN_RESET_PASSWORD_FAIL);
        }
    }

    @Override
    public PageResponse<Admin> pageQueryByState(String keyWord,String nickName, String state, int currentPage, int pageSize) {
        Page<Admin> page = new Page<>(currentPage, pageSize);

        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(state), Admin::getStatus, state);
        queryWrapper.like(StringUtils.isNotBlank(keyWord), Admin::getUsername, keyWord);
        queryWrapper.like(StringUtils.isNotBlank(nickName), Admin::getNickName, nickName);
        queryWrapper.orderBy(true, false, Admin::getCreateTime);
        Page<Admin> adminPage = this.page(page, queryWrapper);

        return PageResponse.of(adminPage.getRecords(), (int) adminPage.getCurrent(), (int) adminPage.getSize(), (int) adminPage.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAdminRole(Long adminId, List<Long> roleIdList) {
        Admin admin = this.getById(adminId);
        checkAdmin(admin);

        LambdaQueryWrapper<AdminRoleRelation> queryWrapper = new LambdaQueryWrapper<AdminRoleRelation>()
                .eq(AdminRoleRelation::getAdminId, adminId);
        adminRoleRelationService.remove(queryWrapper);

        if (!CollUtil.isEmpty(roleIdList)) {
            List<AdminRoleRelation> list = roleIdList.stream()
                    .map(roleId -> {
                        AdminRoleRelation adminRoleRelation = new AdminRoleRelation();
                        adminRoleRelation.setAdminId(adminId);
                        adminRoleRelation.setRoleId(roleId);
                        return adminRoleRelation;
                    }).toList();
            adminRoleRelationService.saveBatch(list);
        }
    }

    @Override
    public List<RoleBasicInfoVO> getRoleListByAdminId(Long adminId) {

        return roleMapper.selectRoleListByAdminId(adminId);
    }

    @Override
    public List<ResourceBasicInfoVO> getResourceListByAdminId(Long adminId) {
        return resourceMapper.selectResourceListByAdminId(adminId);
    }

    @Override
    public List<String> getRoleNameListByAdminId(Long adminId) {
        return this.baseMapper.selectRoleNameListByAdminId(adminId);
    }

    @Override
    public List<String> getPermissionNameListByAdminId(Long adminId) {
        return this.baseMapper.selectPermissionNameListByAdminId(adminId);

    }


}

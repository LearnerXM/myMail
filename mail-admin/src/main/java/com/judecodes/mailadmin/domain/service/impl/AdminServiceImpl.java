package com.judecodes.mailadmin.domain.service.impl;

import com.judecodes.mailadmin.constant.AdminStateEnum;
import com.judecodes.mailadmin.domain.entity.Admin;
import com.judecodes.mailadmin.infrastructure.exception.AdminErrorCode;
import com.judecodes.mailadmin.infrastructure.exception.AdminException;
import com.judecodes.mailadmin.infrastructure.mapper.AdminMapper;
import com.judecodes.mailadmin.domain.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.judecodes.maildatasource.utils.PasswordUtils;
import org.springframework.stereotype.Service;

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

    @Override
    public Admin login(String username, String password) {
        Admin admin = this.lambdaQuery()
                .eq(Admin::getUsername, username)
                .one();
        if(admin == null){
            throw new AdminException(AdminErrorCode.ADMIN_NOT_EXIST);
        }
        if (admin.getStatus()!= AdminStateEnum.ENABLED.getCode()){
            throw new AdminException(AdminErrorCode.ADMIN_STATUS_ERROR);
        }

        if(!PasswordUtils.matches(password, admin.getPassword())){
            throw new AdminException(AdminErrorCode.ADMIN_PASSWD_CHECK_FAIL);
        }
        return admin;
    }
}

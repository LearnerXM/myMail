package com.judecodes.mailgateway.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.judecodes.mailbase.constant.AuthConstant;
import com.judecodes.mailbase.constant.UserType;
import com.judecodes.mailbase.dto.AdminDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的权限列表
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的角色列表

        if (StpUtil.getSession().get(AuthConstant.STP_IDENTITY_TYPE).equals(UserType.ADMIN)){
            AdminDto adminDto = (AdminDto)StpUtil.getSession().get(AuthConstant.ADMIN_ROLE_INFO);
            return adminDto.getRoleList();
        }
        return null;
    }

}


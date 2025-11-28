package com.judecodes.mailadmin.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.judecodes.mailadmin.domain.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author judecodes
 * @since 2025-11-26
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 根据管理员ID查询角色编码列表
     */
    List<String> selectRoleNameListByAdminId(@Param("adminId") Long adminId);

    /**
     * 根据管理员ID查询权限编码列表（可以是 resourceCode 或 permissionCode）
     */
    List<String> selectPermissionNameListByAdminId(@Param("adminId") Long adminId);
}

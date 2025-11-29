package com.judecodes.mailadmin.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.judecodes.mailadmin.domain.entity.Role;
import com.judecodes.mailadmin.vo.RoleBasicInfoVO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author judecodes
 * @since 2025-11-28
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {


     List<RoleBasicInfoVO> selectRoleListByAdminId(@Param("adminId") Long adminId);
}

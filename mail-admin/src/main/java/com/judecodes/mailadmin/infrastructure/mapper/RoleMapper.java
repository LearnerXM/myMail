package com.judecodes.mailadmin.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.judecodes.mailadmin.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author judecodes
 * @since 2025-11-26
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}

package com.judecodes.mailadmin.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.judecodes.mailadmin.domain.entity.RoleMenuRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 后台角色菜单关系表 Mapper 接口
 * </p>
 *
 * @author judecodes
 * @since 2025-11-26
 */
@Mapper
public interface RoleMenuRelationMapper extends BaseMapper<RoleMenuRelation> {

}

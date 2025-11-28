package com.judecodes.mailadmin.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.judecodes.mailadmin.domain.entity.Resource;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author judecodes
 * @since 2025-11-28
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

}

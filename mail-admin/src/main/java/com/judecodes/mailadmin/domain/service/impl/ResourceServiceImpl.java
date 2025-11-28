package com.judecodes.mailadmin.domain.service.impl;

import com.judecodes.mailadmin.domain.entity.Resource;
import com.judecodes.mailadmin.infrastructure.mapper.ResourceMapper;
import com.judecodes.mailadmin.domain.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台资源表 服务实现类
 * </p>
 *
 * @author judecodes
 * @since 2025-11-28
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

}

package com.judecodes.mailuser.facade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.judecodes.mailuser.domin.UserOperateStream;
import com.judecodes.mailuser.facade.service.UserOperateStreamService;
import com.judecodes.mailuser.infrastructure.UserOperateStreamMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户操作流水表 服务实现类
 * </p>
 *
 * @author judecodes
 * @since 2025-11-17
 */
@Service
public class UserOperateStreamServiceImpl extends ServiceImpl<UserOperateStreamMapper, UserOperateStream> implements UserOperateStreamService {

}

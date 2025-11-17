package com.judecodes.mailuser.facade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.judecodes.mailuser.domin.User;
import com.judecodes.mailuser.facade.service.UserService;
import com.judecodes.mailuser.infrastructure.UserMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author judecodes
 * @since 2025-11-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

package com.judecodes.mailadmin.domain.service.impl;

import com.judecodes.mailadmin.domain.entity.Menu;
import com.judecodes.mailadmin.infrastructure.mapper.MenuMapper;
import com.judecodes.mailadmin.domain.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台菜单表 服务实现类
 * </p>
 *
 * @author judecodes
 * @since 2025-11-26
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}

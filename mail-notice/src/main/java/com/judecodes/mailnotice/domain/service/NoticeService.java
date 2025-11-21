package com.judecodes.mailnotice.domain.service;

import com.judecodes.mailnotice.domain.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 通知表 服务类
 * </p>
 *
 * @author judecodes
 * @since 2025-11-19
 */
public interface NoticeService extends IService<Notice> {

     Notice saveCode(String phone,String code,String smsType);

}

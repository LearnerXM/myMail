package com.judecodes.mailnotice.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.judecodes.mailnotice.domain.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 通知表 Mapper 接口
 * </p>
 *
 * @author judecodes
 * @since 2025-11-19
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

}

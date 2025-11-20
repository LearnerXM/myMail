package com.judecodes.mailmember.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.judecodes.mailmember.domain.entity.Member;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author judecodes
 * @since 2025-11-18
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

}

package com.judecodes.mailmember.domain.service;


import com.judecodes.mailapi.member.response.MemberOperatorResponse;
import com.judecodes.mailmember.domain.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author judecodes
 * @since 2025-11-18
 */
public interface MemberService extends IService<Member> {

    Member findById(Long id);
    Member findByPhone(String phone);
    Member findByUsernameAndPassword(String username, String password);
    MemberOperatorResponse smsRegister(String phone);

}

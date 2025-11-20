package com.judecodes.mailmember.domain.service;

import com.judecodes.mailapi.member.request.LoginRequest;
import com.judecodes.mailapi.member.request.SmsLoginRequest;
import com.judecodes.mailapi.member.request.SmsRegisterRequest;
import com.judecodes.mailapi.member.response.MemberVOResponse;
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
    MemberVOResponse login(LoginRequest loginRequest);
    MemberVOResponse smsLogin(SmsLoginRequest smsLoginRequest);
    MemberVOResponse smsRegister(SmsRegisterRequest smsRegisterRequest);

}

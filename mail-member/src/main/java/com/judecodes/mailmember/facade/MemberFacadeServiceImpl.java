package com.judecodes.mailmember.facade;

import com.judecodes.mailapi.member.request.LoginRequest;
import com.judecodes.mailapi.member.request.SmsLoginRequest;
import com.judecodes.mailapi.member.request.SmsRegisterRequest;
import com.judecodes.mailapi.member.response.MemberVOResponse;
import com.judecodes.mailapi.member.service.MemberFacadeService;
import com.judecodes.mailmember.domain.service.MemberService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;


@DubboService(version = "1.0.0")
public class MemberFacadeServiceImpl implements MemberFacadeService {

    @Autowired
    private MemberService memberService;
    @Override
    public MemberVOResponse login(LoginRequest loginRequest) {
        return memberService.login(loginRequest);
    }

    @Override
    public MemberVOResponse smsLogin(SmsLoginRequest smsLoginRequest) {

        return memberService.smsLogin(smsLoginRequest);
    }

    @Override
    public MemberVOResponse smsRegister(SmsRegisterRequest smsRegisterRequest) {
        return memberService.smsRegister(smsRegisterRequest);
    }
}

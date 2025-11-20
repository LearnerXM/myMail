package com.judecodes.mailapi.member.service;

import com.judecodes.mailapi.member.request.LoginRequest;
import com.judecodes.mailapi.member.request.SmsLoginRequest;
import com.judecodes.mailapi.member.request.SmsRegisterRequest;
import com.judecodes.mailapi.member.response.MemberVOResponse;

public interface MemberFacadeService {
     MemberVOResponse login(LoginRequest loginRequest);

     MemberVOResponse smsLogin(SmsLoginRequest smsLoginRequest);

     MemberVOResponse smsRegister(SmsRegisterRequest smsRegisterRequest);
}

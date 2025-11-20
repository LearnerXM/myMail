package com.judecodes.mailauth.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.judecodes.mailapi.member.request.LoginRequest;
import com.judecodes.mailapi.member.request.SmsLoginRequest;
import com.judecodes.mailapi.member.request.SmsRegisterRequest;
import com.judecodes.mailapi.member.response.MemberVOResponse;
import com.judecodes.mailapi.member.service.MemberFacadeService;
import com.judecodes.mailapi.notice.service.NoticeFacadeService;
import com.judecodes.mailauth.param.LoginParam;
import com.judecodes.mailauth.param.SmsLoginParam;
import com.judecodes.mailauth.param.SmsRegisterParam;
import com.judecodes.mailauth.vo.LoginVO;
import com.judecodes.mailbase.validator.Phone;
import com.judecodes.mailweb.vo.Result;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @DubboReference(version = "1.0.0")
    private NoticeFacadeService noticeFacadeService;
    @DubboReference(version = "1.0.0")
    private MemberFacadeService memberFacadeService;

    @PostMapping("/sendCode")
    @Validated
    public Result<String> sendCode(@Phone String phone){

        Boolean result = noticeFacadeService.sendCode(phone);
        Assert.isTrue(result, "验证码发送失败");
        return Result.success("验证码已发送");
    }

    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginParam loginParam){
        LoginRequest loginRequest = new LoginRequest();
        BeanUtil.copyProperties(loginParam, loginRequest);
        MemberVOResponse memberVOResponse = memberFacadeService.login(loginRequest);
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(memberVOResponse.getUserId());
        StpUtil.login(memberVOResponse.getUserId());
        return Result.success(loginVO);
    }

    @PostMapping("/smsLogin")
    public Result<LoginVO> smsLogin(@Valid @RequestBody SmsLoginParam smsLoginParam){
        SmsLoginRequest smsLoginRequest = new SmsLoginRequest();
        BeanUtil.copyProperties(smsLoginParam, smsLoginRequest);
        MemberVOResponse memberVOResponse = memberFacadeService.smsLogin(smsLoginRequest);
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(memberVOResponse.getUserId());
        StpUtil.login(memberVOResponse.getUserId());
        return Result.success(loginVO);
    }

    @PostMapping("/smsRegister")
    public Result<LoginVO> smsRegister(@Valid @RequestBody SmsRegisterParam smsRegisterParam){
        SmsRegisterRequest smsRegisterRequest = new SmsRegisterRequest();
        BeanUtil.copyProperties(smsRegisterParam, smsRegisterRequest);
        MemberVOResponse memberVOResponse = memberFacadeService.smsRegister(smsRegisterRequest);
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(memberVOResponse.getUserId());
        StpUtil.login(memberVOResponse.getUserId());
        return Result.success(loginVO);
    }

    @PostMapping("/logout")
    public Result<Boolean> logout() {
        StpUtil.logout();
        return Result.success(true);
    }
    @GetMapping("/test")
    public String test(){
        return "success";
    }

}

package com.judecodes.mailauth.controller;


import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.judecodes.mailapi.member.request.MemberQueryRequest;
import com.judecodes.mailapi.member.response.MemberOperatorResponse;
import com.judecodes.mailapi.member.response.MemberQueryResponse;
import com.judecodes.mailapi.member.response.data.MemberInfo;
import com.judecodes.mailapi.member.service.MemberFacadeService;
import com.judecodes.mailapi.notice.response.NoticeResponse;
import com.judecodes.mailapi.notice.service.NoticeFacadeService;
import com.judecodes.mailauth.exception.AuthErrorCode;
import com.judecodes.mailauth.exception.AuthException;
import com.judecodes.mailauth.param.LoginParam;
import com.judecodes.mailauth.param.SmsLoginParam;
import com.judecodes.mailauth.param.SmsRegisterParam;
import com.judecodes.mailauth.vo.LoginVO;
import com.judecodes.mailbase.validator.Phone;
import com.judecodes.mailweb.vo.Result;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.judecodes.mailapi.notice.constant.NoticeConstant.CODE_KEY_PREFIX;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {


    @DubboReference(version = "1.0.0")
    private NoticeFacadeService noticeFacadeService;
    @DubboReference(version = "1.0.0")
    private MemberFacadeService memberFacadeService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 开发验证码
     */
    private static final String ROOT_CAPTCHA = "8888";

    /**
     * 默认登录超时时间：7天
     */
    private static final Integer DEFAULT_LOGIN_SESSION_TIMEOUT = 60 * 60 * 24 * 7;

    @GetMapping("/sendCode")//获得信息用Get
    @Validated
    public Result<Boolean> sendCode(@Phone String phone){

        NoticeResponse noticeResponse = noticeFacadeService.sendCode(phone);

        return Result.success(noticeResponse.getSuccess());
    }

    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginParam loginParam){

        MemberQueryRequest memberQueryRequest = new MemberQueryRequest(loginParam.getUsername(), loginParam.getPassword());
        MemberQueryResponse<MemberInfo> memberQueryResponse= memberFacadeService.query(memberQueryRequest);

        MemberInfo memberInfo = memberQueryResponse.getData();
        if (memberInfo == null) {
            throw new AuthException(AuthErrorCode.USER_NOT_EXIST);
        }
        StpUtil.login(memberInfo.getId(),new SaLoginModel().setIsLastingCookie(loginParam.getRememberMe())
                .setTimeout(DEFAULT_LOGIN_SESSION_TIMEOUT));
        StpUtil.getSession().set(memberInfo.getId().toString(),memberInfo);
        LoginVO loginVO = new LoginVO(memberInfo);
        return Result.success(loginVO);
    }

    @PostMapping("/smsLogin")
    public Result<LoginVO> smsLogin(@Valid @RequestBody SmsLoginParam smsLoginParam){

        if (!ROOT_CAPTCHA.equals(smsLoginParam.getCode())) {
            String sendCode = stringRedisTemplate.opsForValue().get(CODE_KEY_PREFIX + smsLoginParam.getPhone());
            if (!StringUtils.equalsIgnoreCase(sendCode, smsLoginParam.getCode())) {
                throw new AuthException(AuthErrorCode.VERIFICATION_CODE_WRONG);
            }
        }

        MemberQueryRequest memberQueryRequest = new MemberQueryRequest(smsLoginParam.getPhone());
        MemberQueryResponse<MemberInfo> memberQueryResponse= memberFacadeService.query(memberQueryRequest);

        MemberInfo memberInfo = memberQueryResponse.getData();
        if (memberInfo == null) {
            MemberOperatorResponse memberVOResponse = memberFacadeService.smsRegister(smsLoginParam.getPhone());

            if (memberVOResponse.getSuccess()){
                memberQueryResponse= memberFacadeService.query(memberQueryRequest);
                memberInfo = memberQueryResponse.getData();
                StpUtil.login(memberInfo.getId(),new SaLoginModel().setIsLastingCookie(smsLoginParam.getRememberMe())
                        .setTimeout(DEFAULT_LOGIN_SESSION_TIMEOUT));
                StpUtil.getSession().set(memberInfo.getId().toString(),memberInfo);
                LoginVO loginVO = new LoginVO(memberInfo);
                return Result.success(loginVO);
            }
            return Result.error(memberVOResponse.getResponseCode(), memberVOResponse.getResponseMessage());
        }

        StpUtil.login(memberInfo.getId(),new SaLoginModel().setIsLastingCookie(smsLoginParam.getRememberMe())
                .setTimeout(DEFAULT_LOGIN_SESSION_TIMEOUT));
        StpUtil.getSession().set(memberInfo.getId().toString(),memberInfo);
        LoginVO loginVO = new LoginVO(memberInfo);
        return Result.success(loginVO);
    }

    @PostMapping("/smsRegister")
    public Result<Boolean> smsRegister(@Valid @RequestBody SmsRegisterParam smsRegisterParam){
        if (!ROOT_CAPTCHA.equals(smsRegisterParam.getCode())) {
            //验证码校验
            String sendCode = stringRedisTemplate.opsForValue().get(CODE_KEY_PREFIX + smsRegisterParam.getPhone());
            if (!StringUtils.equalsIgnoreCase(sendCode, smsRegisterParam.getCode())) {
                throw new AuthException(AuthErrorCode.VERIFICATION_CODE_WRONG);
            }
        }
        MemberOperatorResponse memberVOResponse = memberFacadeService.smsRegister(smsRegisterParam.getPhone());

        if (memberVOResponse.getSuccess()){
            return Result.success(true);
        }

        return Result.error(memberVOResponse.getResponseCode(), memberVOResponse.getResponseMessage());
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

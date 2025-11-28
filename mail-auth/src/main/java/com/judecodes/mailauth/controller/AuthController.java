package com.judecodes.mailauth.controller;


import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.judecodes.mailapi.member.request.MemberQueryRequest;
import com.judecodes.mailapi.member.response.MemberOperatorResponse;
import com.judecodes.mailapi.member.response.MemberQueryResponse;
import com.judecodes.mailapi.member.response.data.MemberInfo;
import com.judecodes.mailapi.member.service.MemberFacadeService;
import com.judecodes.mailapi.notice.constant.SmsType;
import com.judecodes.mailapi.notice.response.NoticeResponse;
import com.judecodes.mailapi.notice.resquest.SendCodeRequest;
import com.judecodes.mailapi.notice.service.NoticeFacadeService;
import com.judecodes.mailauth.exception.AuthErrorCode;
import com.judecodes.mailauth.exception.AuthException;
import com.judecodes.mailauth.param.LoginParam;
import com.judecodes.mailauth.param.SmsLoginParam;
import com.judecodes.mailauth.param.SmsRegisterParam;
import com.judecodes.mailauth.vo.LoginVO;
import com.judecodes.mailbase.constant.AuthConstant;
import com.judecodes.mailbase.constant.UserType;
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

    @GetMapping("/sendAuthCode")//获得信息用Get
    @Validated
    public Result<Boolean> sendAuthCode(@Phone String phone){

        SendCodeRequest sendCodeRequest = new SendCodeRequest();
        sendCodeRequest.setPhone(phone);
        sendCodeRequest.setSmsType(SmsType.AUTH);
        NoticeResponse noticeResponse = noticeFacadeService.sendCode(sendCodeRequest);

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
        StpUtil.getSession().set(AuthConstant.STP_IDENTITY_TYPE, UserType.USER);
        LoginVO loginVO = new LoginVO(memberInfo);
        return Result.success(loginVO);
    }

    @PostMapping("/smsLogin")
    public Result<LoginVO> smsLogin(@Valid @RequestBody SmsLoginParam smsLoginParam){

        if (!ROOT_CAPTCHA.equals(smsLoginParam.getCode())) {
            String cacheCode = stringRedisTemplate.opsForValue().get(SmsType.AUTH.buildRedisKey(smsLoginParam.getPhone()));
            if (!StringUtils.equalsIgnoreCase(cacheCode, smsLoginParam.getCode())) {
                throw new AuthException(AuthErrorCode.VERIFICATION_CODE_WRONG);
            }
            //TODO 短信检验成功后更改通知状态
            //短信检验成功后更改通知状态
            stringRedisTemplate.delete(SmsType.AUTH.buildRedisKey(smsLoginParam.getPhone()));
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
            String cacheCode = stringRedisTemplate.opsForValue().get(SmsType.AUTH.buildRedisKey(smsRegisterParam.getPhone()));
            if (!StringUtils.equalsIgnoreCase(cacheCode, smsRegisterParam.getCode())) {
                throw new AuthException(AuthErrorCode.VERIFICATION_CODE_WRONG);
            }
            //TODO
            //短信检验成功后更改通知状态
            stringRedisTemplate.delete(SmsType.AUTH.buildRedisKey(smsRegisterParam.getPhone()));
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

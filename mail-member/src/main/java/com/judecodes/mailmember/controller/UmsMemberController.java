package com.judecodes.mailmember.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.judecodes.mailapi.member.constant.MemberStateEnum;
import com.judecodes.mailapi.member.response.data.BasicMemberInfo;
import com.judecodes.mailapi.member.response.data.MemberInfo;
import com.judecodes.mailapi.notice.constant.SmsType;
import com.judecodes.mailapi.notice.response.NoticeResponse;
import com.judecodes.mailapi.notice.resquest.SendCodeRequest;
import com.judecodes.mailapi.notice.service.NoticeFacadeService;
import com.judecodes.mailbase.validator.Phone;
import com.judecodes.mailmember.domain.entity.Member;
import com.judecodes.mailmember.domain.service.MemberService;
import com.judecodes.mailmember.infrastructure.exception.MemberErrorCode;
import com.judecodes.mailmember.infrastructure.exception.MemberException;
import com.judecodes.mailmember.param.ModifyPasswordParam;
import com.judecodes.mailmember.param.VerifyCodeParam;
import com.judecodes.mailweb.vo.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.judecodes.mailapi.member.constant.MemberConstants.PWD_CHANGE_TOKEN;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author judecodes
 * @since 2025-11-18
 */
@RestController
@RequestMapping("/member")
@Validated
public class UmsMemberController {

    @DubboReference(version = "1.0.0")
    private NoticeFacadeService noticeFacadeService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @GetMapping("/getMemberInfo")
    public Result<MemberInfo> getMemberInfo(){
        String memberId = (String)StpUtil.getLoginId();
        Member member = memberService.findById(Long.parseLong(memberId) );
        if (member==null){
            throw new MemberException(MemberErrorCode.USER_NOT_EXIST);
        }
        MemberInfo memberInfo = new MemberInfo();
        BeanUtils.copyProperties(member,memberInfo);
        return Result.success(memberInfo);
    }
    @GetMapping("/queryUserByTel")
    public Result<BasicMemberInfo> queryMemberByTel(@Phone String phone){

        Member member = memberService.findByPhone(phone);
        if (member==null){
            throw new MemberException(MemberErrorCode.USER_NOT_EXIST);
        }
        BasicMemberInfo basicMemberInfo = new BasicMemberInfo();
        BeanUtils.copyProperties(member,basicMemberInfo);
        return Result.success(basicMemberInfo);
    }


    @PostMapping("/modifyNickName")
    public Result<Boolean> modifyNickName(String nickName){
        String memberId = (String)StpUtil.getLoginId();
        Member member = memberService.findById(Long.parseLong(memberId) );
        if (member.getStatus()!= MemberStateEnum.ENABLED.getCode()){
            throw new MemberException(MemberErrorCode.USER_STATUS_ERROR);
        }
        if (StringUtils.isBlank(nickName)){
            throw new MemberException(MemberErrorCode.NICK_NAME_IS_NULL);
        }
        memberService.modifyNickName(memberId,nickName);
        return Result.success(true);
    }
    @GetMapping("/sendPasswordChangeCode")//获得信息用Get

    public Result<Boolean> sendPasswordChangeCode(@Phone String phone){
        if (StpUtil.isLogin()){
            String memberId = (String)StpUtil.getLoginId();
            Member member = memberService.findById(Long.parseLong(memberId) );
            if (member.getStatus()!= MemberStateEnum.ENABLED.getCode()){
                throw new MemberException(MemberErrorCode.USER_STATUS_ERROR);
            }
            String loginPhone = member.getPhone();
            if (!StringUtils.equals(loginPhone,phone)){
                throw new MemberException(MemberErrorCode.ILLEGAL_PHONE);
            }
        }

        SendCodeRequest sendCodeRequest = new SendCodeRequest();
        sendCodeRequest.setPhone(phone);
        sendCodeRequest.setSmsType(SmsType.PASSWORD_CHANGE);
        NoticeResponse noticeResponse = noticeFacadeService.sendCode(sendCodeRequest);

        return Result.success(noticeResponse.getSuccess());
    }

    @PostMapping("/verifyCode")
    public Result<String> verifyCode(@Valid @RequestBody VerifyCodeParam verifyCodeParam) {
        String phone = verifyCodeParam.getPhone();
        String code = verifyCodeParam.getCode();
        String result = stringRedisTemplate.opsForValue().get(SmsType.PASSWORD_CHANGE.buildRedisKey(phone));
        if(!StringUtils.equalsIgnoreCase(result,code)){
            throw new MemberException(MemberErrorCode.CODE_ERROR);
        }
        stringRedisTemplate.delete(SmsType.PASSWORD_CHANGE.buildRedisKey(phone));
        //TODO 短信检验成功后更改通知状态

        // 生成一次性 token
        String changeToken = UUID.randomUUID().toString().replace("-", "");
        String tokenKey =  PWD_CHANGE_TOKEN+ phone;
        stringRedisTemplate.opsForValue().set(tokenKey, changeToken, 10, TimeUnit.MINUTES);

        return Result.success(changeToken);
    }
    @PostMapping("/modifyPassword")
    public Result<Boolean> modifyPassword(@Valid @RequestBody ModifyPasswordParam modifyPasswordParam){
        String memberId = (String)StpUtil.getLoginId();
        Member member = memberService.findById(Long.parseLong(memberId) );
        if (member.getStatus()!= MemberStateEnum.ENABLED.getCode()){
            throw new MemberException(MemberErrorCode.USER_STATUS_ERROR);
        }
        String phone = member.getPhone();

        String changeToken = modifyPasswordParam.getChangeToken();
        String password = modifyPasswordParam.getPassword();
        String cacheToken = stringRedisTemplate.opsForValue().get(PWD_CHANGE_TOKEN + phone);
        if (!StringUtils.equals(changeToken,cacheToken)){
            throw new MemberException(MemberErrorCode.TOKEN_ERROR);
        }
        stringRedisTemplate.delete(PWD_CHANGE_TOKEN + phone);
        memberService.modifyPassword(memberId,password);
        StpUtil.logout();
        return Result.success(true);
    }
    @PostMapping("/modifySignature")
    public Result<Boolean> modifySignature(@Size(max = 128, message = "个性签名长度不能超过128个字符") String signature){
        String memberId = (String)StpUtil.getLoginId();
        Member member = memberService.findById(Long.parseLong(memberId) );
        if (member.getStatus()!= MemberStateEnum.ENABLED.getCode()){
            throw new MemberException(MemberErrorCode.USER_STATUS_ERROR);
        }
        memberService.modifySignature(memberId,signature);
        return Result.success(true);
    }
    //TODO 上传头像功能
    @PostMapping("/modifyProfilePhoto")
    public Result<String> modifyProfilePhoto(){
        return null;
    }
    /**
     * 测试
     */
    @GetMapping("/test")
    public String test() {
        return "test";
    }

}

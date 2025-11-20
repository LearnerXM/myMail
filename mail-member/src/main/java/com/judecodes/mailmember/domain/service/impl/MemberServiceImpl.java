package com.judecodes.mailmember.domain.service.impl;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.judecodes.mailapi.member.constant.GenderEnum;
import com.judecodes.mailapi.member.constant.MemberStateEnum;
import com.judecodes.mailapi.member.request.LoginRequest;
import com.judecodes.mailapi.member.request.SmsLoginRequest;
import com.judecodes.mailapi.member.request.SmsRegisterRequest;
import com.judecodes.mailapi.member.response.MemberVOResponse;

import com.judecodes.mailmember.domain.entity.Member;

import com.judecodes.mailmember.domain.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.judecodes.mailmember.infrastructure.exception.UserErrorCode;
import com.judecodes.mailmember.infrastructure.exception.UserException;
import com.judecodes.mailmember.infrastructure.mapper.MemberMapper;
import com.judecodes.mailmember.infrastructure.util.PasswordUtils;
import com.judecodes.mailmember.infrastructure.util.RandomPasswordGenerator;
import com.judecodes.mailmember.infrastructure.util.UsernameGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import static com.judecodes.mailapi.notice.constant.NoticeConstant.CODE_KEY_PREFIX;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author judecodes
 * @since 2025-11-18
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public MemberVOResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>().eq("username", username);
        Member member = this.getOne(queryWrapper);
        Assert.notNull(member, UserErrorCode.USER_NOT_EXIST.getMessage());
        boolean matches = PasswordUtils.matches(password, member.getPassword());
        Assert.isFalse(matches, UserErrorCode.USER_PASSWD_CHECK_FAIL.getMessage());
        MemberVOResponse memberVOResponse = new MemberVOResponse();
        memberVOResponse.setUserId(member.getId());
        return memberVOResponse;
    }

    @Override
    public MemberVOResponse smsLogin(SmsLoginRequest smsLoginRequest) {
        String phone = smsLoginRequest.getPhone();
        String code = smsLoginRequest.getCode();
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>().eq("phone", phone);
        Member member = this.getOne(queryWrapper);
        if (member == null) {
            SmsRegisterRequest smsRegisterRequest = new SmsRegisterRequest();
            smsRegisterRequest.setPhone(phone);
            smsRegisterRequest.setCode(code);
            return smsRegister(smsRegisterRequest);
        }
        String sendCode = stringRedisTemplate.opsForValue().get(CODE_KEY_PREFIX + phone);
        if (!code.equals(sendCode)) {
            throw new UserException(UserErrorCode.CODE_ERROR);
        }
        stringRedisTemplate.delete(CODE_KEY_PREFIX + phone);

        MemberVOResponse memberVOResponse = new MemberVOResponse();
        memberVOResponse.setUserId(member.getId());
        return memberVOResponse;
    }

    @Override
    public MemberVOResponse smsRegister(SmsRegisterRequest smsRegisterRequest) {
        String code = smsRegisterRequest.getCode();
        String phone = smsRegisterRequest.getPhone();
        String sendCode = stringRedisTemplate.opsForValue().get(CODE_KEY_PREFIX + phone);
        if (!code.equals(sendCode)) {
            throw new UserException(UserErrorCode.CODE_ERROR);
        }
        stringRedisTemplate.delete(CODE_KEY_PREFIX + phone);
        Member member = new Member();
        member.setPhone(phone);
        member.setPassword(PasswordUtils.encode(RandomPasswordGenerator.generate()));
        String username;
        boolean exists;
        do {
            username= UsernameGenerator.generate();
            QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>().eq("username", username);
            exists = this.exists(queryWrapper);
        }while (exists);
        member.setUsername(username);
        member.setNickname(username);
        member.setGender(GenderEnum.SECRET.getCode());
        member.setStatus(MemberStateEnum.ENABLED.getCode());
        boolean result = this.save(member);
        Assert.isTrue(result, "注册失败");
        MemberVOResponse memberVOResponse = new MemberVOResponse();
        memberVOResponse.setUserId(member.getId());
        return memberVOResponse;
    }
}

package com.judecodes.mailmember.domain.service.impl;


import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.template.QuickConfig;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.judecodes.mailapi.member.constant.GenderEnum;

import com.judecodes.mailapi.member.constant.MemberStateEnum;
import com.judecodes.mailapi.member.response.MemberOperatorResponse;

import com.judecodes.mailmember.domain.entity.Member;

import com.judecodes.mailmember.domain.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.judecodes.mailmember.infrastructure.exception.MemberErrorCode;
import com.judecodes.mailmember.infrastructure.exception.MemberException;
import com.judecodes.mailmember.infrastructure.mapper.MemberMapper;
import com.judecodes.mailmember.infrastructure.util.PasswordUtils;
import com.judecodes.mailmember.infrastructure.util.RandomPasswordGenerator;
import com.judecodes.mailmember.infrastructure.util.UsernameGenerator;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.judecodes.mailapi.member.constant.MemberConstants.*;


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
    private CacheManager cacheManager;
    /**
     * 通过用户ID对用户信息做的缓存
     */

    private Cache<String, Member> idMemberCache;

    @PostConstruct
    public void init() {
        QuickConfig idQc = QuickConfig.newBuilder(":member:cache:id:")
                .cacheType(CacheType.BOTH)
                .expire(Duration.ofHours(2))
                .syncLocal(true)
                .build();
        idMemberCache = cacheManager.getOrCreateCache(idQc);
    }


    @Override
    @Cached(name = ":member:cache:id:", cacheType = CacheType.BOTH, key = "#memberId", cacheNullValue = true)
    @CacheRefresh(refresh = 60, timeUnit = TimeUnit.MINUTES)
    public Member findById(Long id) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("id", id);
        return this.getOne(memberQueryWrapper);
    }

    @Override
    public Member findByPhone(String phone) {
        LambdaQueryWrapper<Member> memberQueryWrapper = new LambdaQueryWrapper<>();
        memberQueryWrapper.eq(Member::getPhone, phone);
        Member member = this.getOne(memberQueryWrapper);
        if (member == null) {
            throw new MemberException(MemberErrorCode.USER_NOT_EXIST);
        }
        return member;
    }

    @Override
    public Member findByUsernameAndPassword(String username, String password) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("username", username);
        Member member = this.getOne(memberQueryWrapper);
        if (member == null) {
            throw new MemberException(MemberErrorCode.USER_NOT_EXIST);
        }
        boolean matches = PasswordUtils.matches(password, member.getPassword());
        if (!matches) {
            throw new MemberException(MemberErrorCode.USER_PASSWD_CHECK_FAIL);
        }
        return member;
    }

    @Override
    public MemberOperatorResponse smsRegister(String phone) {

        QueryWrapper<Member> phoneQueryWrapper = new QueryWrapper<Member>().eq("phone", phone);
        boolean existsMember = this.exists(phoneQueryWrapper);
        if (existsMember) {
            throw new MemberException(MemberErrorCode.DUPLICATE_TELEPHONE_NUMBER);
        }

        String username;
        boolean exists;
        do {
            username = UsernameGenerator.generate();
            QueryWrapper<Member> usernameQueryWrapper = new QueryWrapper<Member>().eq("username", username);
            exists = this.exists(usernameQueryWrapper);
        } while (exists);

        Member member = Member.builder()
                .phone(phone)
                .password(PasswordUtils.encode(RandomPasswordGenerator.generate()))
                .username(username)
                .nickname(username)
                .memberLevelId(DEFAULT_MEMBER_LEVEL_ID)
                .integration(DEFAULT_INTEGRATION)
                .growth(DEFAULT_GROWTH)
                .gender(GenderEnum.SECRET.getCode())
                .status(MemberStateEnum.ENABLED.getCode())
                .luckeyCount(DEFAULT_LUCKY_COUNT)
                .historyIntegration(DEFAULT_HISTORY_INTEGRATION)
                .build();
        boolean result = this.save(member);


        if (!result) {
            throw new MemberException(MemberErrorCode.USER_OPERATE_FAILED);
        }
        MemberOperatorResponse memberVOResponse = new MemberOperatorResponse();
        memberVOResponse.setSuccess(true);
        return memberVOResponse;
    }
    public void modifyNickName(String memberId,String nickName){
        boolean result = this.lambdaUpdate()
                .set(Member::getNickname, nickName)
                .eq(Member::getId, Long.parseLong(memberId))
                .update();
        if (!result) {
            throw new MemberException(MemberErrorCode.USER_MODIFY_NICKNAME_FAIL);
        }
    }
    public void modifyPassword(String memberId,String password){

        boolean result = this.lambdaUpdate()
                .set(Member::getPassword, PasswordUtils.encode(password))
                .eq(Member::getId, Long.parseLong(memberId))
                .update();
        if (!result) {
            throw new MemberException(MemberErrorCode.USER_MODIFY_PASSWORD_FAIL);
        }
    }

    @Override
    public void modifySignature(String memberId, String signature) {
        boolean result = this.lambdaUpdate()
                .set(Member::getPersonalizedSignature, signature)
                .eq(Member::getId, Long.parseLong(memberId))
                .update();

        if (!result) {
            throw new MemberException(MemberErrorCode.USER_MODIFY_SIGNATURE_FAIL);
        }
    }

}

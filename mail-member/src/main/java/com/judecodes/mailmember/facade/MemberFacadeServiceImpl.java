package com.judecodes.mailmember.facade;


import com.judecodes.mailapi.member.request.MemberQueryRequest;
import com.judecodes.mailapi.member.request.condition.MemberIdQueryCondition;
import com.judecodes.mailapi.member.request.condition.MemberPhoneQueryCondition;
import com.judecodes.mailapi.member.request.condition.MemberUsernameAndPasswordQueryCondition;
import com.judecodes.mailapi.member.response.MemberOperatorResponse;
import com.judecodes.mailapi.member.response.MemberQueryResponse;
import com.judecodes.mailapi.member.response.data.MemberInfo;
import com.judecodes.mailapi.member.service.MemberFacadeService;
import com.judecodes.mailmember.domain.entity.Member;
import com.judecodes.mailmember.domain.service.MemberService;
import com.judecodes.mailrpc.facade.Facade;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


@DubboService(version = "1.0.0")
public class MemberFacadeServiceImpl implements MemberFacadeService {

    @Autowired
    private MemberService memberService;


    @Override
    public MemberQueryResponse<MemberInfo> query(MemberQueryRequest memberQueryRequest) {


        Member member = switch (memberQueryRequest.getMemberQueryCondition()) {
            case MemberIdQueryCondition memberIdQueryCondition ->
                    memberService.findById(memberIdQueryCondition.getMemberId());
            case MemberPhoneQueryCondition memberPhoneQueryCondition ->
                    memberService.findByPhone(memberPhoneQueryCondition.getPhone());
            case MemberUsernameAndPasswordQueryCondition memberUsernameAndPasswordQueryCondition ->
                    memberService.findByUsernameAndPassword(memberUsernameAndPasswordQueryCondition.getUsername(), memberUsernameAndPasswordQueryCondition.getPassword());
            default ->
                    throw new UnsupportedOperationException(memberQueryRequest.getMemberQueryCondition() + " is not supported");
        };

        MemberInfo memberInfo = new MemberInfo();
        BeanUtils.copyProperties(member, memberInfo);
        MemberQueryResponse<MemberInfo> memberQueryResponse = new MemberQueryResponse<>();
        memberQueryResponse.setData(memberInfo);
        memberQueryResponse.setSuccess(true);
        return memberQueryResponse;
    }

    @Override
    @Facade
    public MemberOperatorResponse smsRegister(String phone) {
        return memberService.smsRegister(phone);
    }
}

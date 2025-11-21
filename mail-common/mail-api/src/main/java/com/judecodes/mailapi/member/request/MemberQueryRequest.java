package com.judecodes.mailapi.member.request;

import com.judecodes.mailapi.member.request.condition.MemberIdQueryCondition;
import com.judecodes.mailapi.member.request.condition.MemberPhoneQueryCondition;
import com.judecodes.mailapi.member.request.condition.MemberQueryCondition;
import com.judecodes.mailapi.member.request.condition.MemberUsernameAndPasswordQueryCondition;
import com.judecodes.mailbase.request.BaseRequest;
import lombok.Data;

@Data
public class MemberQueryRequest extends BaseRequest {

    private MemberQueryCondition memberQueryCondition;

    public MemberQueryRequest(Long memberId){
        MemberIdQueryCondition memberIdQueryCondition = new MemberIdQueryCondition();
        memberIdQueryCondition.setMemberId(memberId);
        this.memberQueryCondition= memberIdQueryCondition;
    }

    public MemberQueryRequest(String phone){
        MemberPhoneQueryCondition memberPhoneQueryCondition = new MemberPhoneQueryCondition();
        memberPhoneQueryCondition.setPhone(phone);
        this.memberQueryCondition= memberPhoneQueryCondition;
    }

    public MemberQueryRequest(String username,String password){
        MemberUsernameAndPasswordQueryCondition memberUsernameAndPasswordQueryCondition = new MemberUsernameAndPasswordQueryCondition();
        memberUsernameAndPasswordQueryCondition.setUsername(username);
        memberUsernameAndPasswordQueryCondition.setPassword(password);
        this.memberQueryCondition= memberUsernameAndPasswordQueryCondition;
    }
}

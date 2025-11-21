package com.judecodes.mailapi.member.service;


import com.judecodes.mailapi.member.request.MemberQueryRequest;

import com.judecodes.mailapi.member.response.MemberOperatorResponse;
import com.judecodes.mailapi.member.response.MemberQueryResponse;
import com.judecodes.mailapi.member.response.data.MemberInfo;

public interface MemberFacadeService {


     MemberQueryResponse<MemberInfo> query(MemberQueryRequest memberQueryRequest);

     MemberOperatorResponse smsRegister(String phone);
}

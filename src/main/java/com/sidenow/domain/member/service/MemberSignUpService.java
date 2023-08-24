package com.sidenow.domain.member.service;

import com.sidenow.domain.member.dto.req.MemberRequest;
import com.sidenow.domain.member.dto.res.MemberResponse;

public interface MemberSignUpService {
    MemberResponse.MemberCheck signUp(MemberRequest.SignUpMemberRequest signUpMemberRequest);
}

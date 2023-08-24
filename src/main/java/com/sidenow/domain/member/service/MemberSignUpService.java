package com.sidenow.domain.member.service;

import com.sidenow.domain.member.dto.req.MemberRequest;
import com.sidenow.domain.member.dto.req.MemberRequest.SignUpMemberRequest;
import com.sidenow.domain.member.dto.res.MemberResponse;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberCheck;

public interface MemberSignUpService {
    MemberCheck signUp(SignUpMemberRequest signUpMemberRequest);
    boolean checkEmailDuplicate(String email);
    boolean checkNicknameDuplicate(String nickname);
}

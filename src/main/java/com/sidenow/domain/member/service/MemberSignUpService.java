package com.sidenow.domain.member.service;

import com.sidenow.domain.member.dto.req.MemberRequest.SignUpMemberRequest;
import com.sidenow.domain.member.entity.Member;

public interface MemberSignUpService {
    Member signUp(SignUpMemberRequest signUpMemberRequest);
    boolean checkEmailDuplicate(String email);
    boolean checkNicknameDuplicate(String nickname);
}

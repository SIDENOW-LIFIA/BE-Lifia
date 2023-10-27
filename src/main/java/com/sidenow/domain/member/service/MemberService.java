package com.sidenow.domain.member.service;

import com.sidenow.domain.member.dto.req.MemberRequest.MemberSignUpRequest;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberSimpleResponse;
import com.sidenow.domain.member.entity.Member;

import java.util.List;

public interface MemberService {
    Member signUp(MemberSignUpRequest memberSignUpRequest);
    List<MemberSimpleResponse> findAllMembers();
    boolean checkEmailDuplicate(String email);
    boolean checkNicknameDuplicate(String nickname);
}

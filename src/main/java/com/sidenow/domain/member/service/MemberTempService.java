package com.sidenow.domain.member.service;

import com.sidenow.domain.member.dto.MemberDto;
import com.sidenow.domain.member.dto.MemberDto.DeleteAccountRequest;
import com.sidenow.domain.member.dto.MemberDto.LoginResponse;
import com.sidenow.domain.member.dto.req.MemberRequest.SignUpMemberRequest;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberCheck;

public interface MemberTempService {


    LoginResponse kakaoLogin(MemberDto.LoginRequest loginRequest);
    LoginResponse socialLoginSignUp(MemberDto.AdditionInfoRequest additionInfoRequest);
    void kakaoLogout(MemberDto.LoginRequest loginRequest);
    void deleteAccount(DeleteAccountRequest deleteAccountRequest);
    LoginResponse testLogin(MemberDto.TestLoginRequest testLoginRequest);
}
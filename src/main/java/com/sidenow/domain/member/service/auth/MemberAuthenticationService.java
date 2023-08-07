package com.sidenow.domain.member.service.auth;

import com.sidenow.domain.member.dto.MemberDto;
import com.sidenow.domain.member.dto.MemberDto.DeleteAccountRequest;
import com.sidenow.domain.member.dto.MemberDto.LoginResponse;
import com.sidenow.domain.member.dto.MemberDto.SignUpRequest;
import com.sidenow.domain.member.entity.Member;

public interface MemberAuthenticationService {

    Member signUp(SignUpRequest signUpRequest);
    LoginResponse login(MemberDto.LoginRequest loginRequest);
    LoginResponse socialLoginSignUp(MemberDto.AdditionInfoRequest additionInfoRequest);
    void logout(MemberDto.LoginRequest loginRequest);
    void deleteAccount(DeleteAccountRequest deleteAccountRequest);
    LoginResponse testLogin(MemberDto.TestLoginRequest testLoginRequest);
}
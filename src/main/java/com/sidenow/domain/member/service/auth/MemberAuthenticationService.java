package com.sidenow.domain.member.service.auth;

import com.sidenow.domain.member.dto.MemberDto;
import com.sidenow.domain.member.dto.MemberDto.DeleteAccountRequest;
import com.sidenow.domain.member.dto.MemberDto.LoginResponse;

public interface MemberAuthenticationService {
    LoginResponse login(MemberDto.LoginRequest loginRequest);
    LoginResponse signup(MemberDto.AdditionInfoRequest additionInfoRequest);
    void logout(MemberDto.LoginRequest loginRequest);
    void deleteAccount(DeleteAccountRequest deleteAccountRequest);
    LoginResponse testLogin(MemberDto.TestLoginRequest testLoginRequest);
}
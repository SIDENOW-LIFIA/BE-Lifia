package com.sidenow.domain.member.service;

import com.sidenow.domain.member.dto.req.MemberRequest;
import com.sidenow.domain.member.dto.req.MemberRequest.MemberLoginRequest;
import com.sidenow.domain.member.dto.req.MemberRequest.MemberTokenRequest;
import com.sidenow.domain.member.dto.res.MemberResponse;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberLoginResponse;

public interface MemberAuthService {

    MemberLoginResponse login(MemberLoginRequest memberLoginRequest);
    MemberLoginResponse reIssueToken(MemberTokenRequest req);
}

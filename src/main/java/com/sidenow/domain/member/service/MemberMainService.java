package com.sidenow.domain.member.service;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.dto.MemberDto.CheckNicknameResponse;

public interface MemberMainService {
    CheckNicknameResponse checkNickname(String nickname);
    Member validateEmail(String email);
}

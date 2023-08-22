package com.sidenow.domain.member.service.validate;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.dto.MemberDto.CheckNicknameResponse;

public interface MemberValidationService {
    CheckNicknameResponse checkNickname(String nickname);
    Member validateEmail(String email);
}

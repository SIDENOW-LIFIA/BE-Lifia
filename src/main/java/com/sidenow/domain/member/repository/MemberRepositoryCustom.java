package com.sidenow.domain.member.repository;

import com.sidenow.domain.member.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
}

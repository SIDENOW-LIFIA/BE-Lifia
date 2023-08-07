package com.sidenow.domain.member.repository;

import com.sidenow.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
}

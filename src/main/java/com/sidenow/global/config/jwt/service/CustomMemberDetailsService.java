package com.sidenow.global.config.jwt.service;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.exception.MemberEmailNotFoundException;
import com.sidenow.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// 인증에 필요한 UserDetailsService 인터페이스의 loadUserByUsername 메서드를 구현하는 클래스로,
// loadUserByUsername 메서드를 통해 DB에 접근하여 사용자 정보를 가지고 옴
@RequiredArgsConstructor
@Service
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {

        Optional<Member> member = memberRepository.findByEmail(memberEmail);
        return new CustomMemberDetails(member.orElseThrow(MemberEmailNotFoundException::new));
    }
}

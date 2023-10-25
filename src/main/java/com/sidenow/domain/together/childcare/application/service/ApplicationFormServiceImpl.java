package com.sidenow.domain.together.childcare.application.service;

import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.domain.together.childcare.board.repository.ChildcareRepository;
import com.sidenow.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class ApplicationServiceImpl implements ApplicationService{
    private final MemberRepository memberRepository;
    private final ChildcareRepository childcareRepository;
    private final SecurityUtils securityUtils;

    public

}

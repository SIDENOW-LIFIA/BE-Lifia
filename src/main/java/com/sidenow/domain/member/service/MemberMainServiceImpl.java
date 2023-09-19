package com.sidenow.domain.member.service;


import com.sidenow.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberMainServiceImpl implements MemberMainService {

    private final MemberRepository memberRepository;





}

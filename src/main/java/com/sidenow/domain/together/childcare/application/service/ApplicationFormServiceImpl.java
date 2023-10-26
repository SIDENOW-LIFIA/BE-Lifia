package com.sidenow.domain.together.childcare.application.service;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.exception.MemberNotLoginException;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.domain.together.childcare.application.dto.req.ApplicationFormRequest.ApplicationFormCreateRequest;
import com.sidenow.domain.together.childcare.application.dto.res.ApplicationFormResponse.ApplicationFormCreateResponse;
import com.sidenow.domain.together.childcare.application.entity.ApplicationForm;
import com.sidenow.domain.together.childcare.application.repository.ApplicationFormRepository;
import com.sidenow.domain.together.childcare.board.entity.Childcare;
import com.sidenow.domain.together.childcare.board.exception.ChildcareIdNotFoundException;
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
public class ApplicationFormServiceImpl implements ApplicationFormService {
    private final MemberRepository memberRepository;
    private final ChildcareRepository childcareRepository;
    private final ApplicationFormRepository applicationFormRepository;
    private final SecurityUtils securityUtils;

    @Override
    public ApplicationFormCreateResponse createApplicationForm(Long childcareId, ApplicationFormCreateRequest req){
        log.info("Create Application Form Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());
        Childcare childcare = childcareRepository.findByChildcareId(childcareId)
                .orElseThrow(ChildcareIdNotFoundException::new);
        ApplicationForm applicationForm = ApplicationFormCreateRequest.to(req, member, childcare);
        applicationFormRepository.save(applicationForm);
        log.info("Create Application Form Service 종료");

        return ApplicationFormCreateResponse.from(applicationForm);
    }
}

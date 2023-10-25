package com.sidenow.domain.together.childcare.application.repository;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.together.childcare.application.entity.ApplicationForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationFormRepository extends JpaRepository<ApplicationForm, Long> {
    List<ApplicationForm> findAllByMember(Member member);
}

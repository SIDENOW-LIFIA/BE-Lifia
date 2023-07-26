package com.sidenow.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // 응답결과가 여러건인 경우, 리포지토리 메서드 리턴타입을 List<Question>로 해야함.
    Question findBySubject(String subject);
    Question findBySubjectAndContent (String subject, String content);
    List<Question> findBySubjectLike (String subject);
}

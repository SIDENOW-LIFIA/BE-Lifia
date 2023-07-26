package com.sidenow.domain.question;

import com.sidenow.domain.answer.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    // Answer 엔티티 객체로 구성된 answerList를 속성으로 추가
    // 질문 객체(예:question)에서 답변을 참조하려면 question.getAnswerList()를 호출하면 됨
    // Answer 엔티티에서 Question 엔티티를 참조한 속성명 question을 mappedBy에 전달해야함
    // 질문을 삭제하면 그에 달린 답변들도 모두 함께 삭제하기 위해서 @OneToMany의 속성으로 cascade = CascadeType.REMOVE를 사용
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}

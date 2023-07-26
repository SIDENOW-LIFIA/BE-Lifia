package com.sidenow.domain.answer;

import com.sidenow.domain.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    // 질문 엔티티와 연결된 속성이라는 것을 명시적으로 표시
    // Answer 엔티티의 question 속성과 Question 엔티티가 서로 연결
    // DB에서는 ForeignKey 관계 생성
    @ManyToOne
    private Question question; // 답변 엔티티에서 질문 엔티티를 참조
}

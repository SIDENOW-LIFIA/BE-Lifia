package com.sidenow.domain.together.childcare.application.entity;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.together.childcare.board.entity.Childcare;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class Form extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id")
    private Long formId;
    @Column(nullable = false)
    private String apartment;
    @Column(nullable = false)
    private int childAge;
    @Column(nullable = false)
    private String phoneNumber;
    @Column
    @Lob
    private String note;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "childcare_id", nullable = false)
    private Childcare childcare;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 공동육아 신청자

}

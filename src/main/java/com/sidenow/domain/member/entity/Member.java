package com.sidenow.domain.member.entity;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.member.constant.MemberConstant.Provider;
import com.sidenow.domain.member.constant.MemberConstant.Role;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // 추가정보
    @Column(length = 10, nullable = false)
    private String name;

    @Column(unique = true, length = 20, nullable = false)
    private String nickname;

    @Column(nullable = false, length = 50)
    private String apartment;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime loginAt;

    // Kakao, Google, Naver 등
    private Provider provider;

    // 작성한 자유게시판 게시글
    @OneToMany(mappedBy = "member")
    private List<FreeBoard> freeBoards = new ArrayList<>();

    // 작성한 자유게시판 게시글의 댓글
    @OneToMany(mappedBy = "member")
    private List<FreeBoardComment> freeBoardComments = new ArrayList<>();

    // 유저 로그인 시간 업데이트
    public void updateLoginAt(LocalDateTime now) {
        this.loginAt = now;
    }

    // 유저 닉네임 변경
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}

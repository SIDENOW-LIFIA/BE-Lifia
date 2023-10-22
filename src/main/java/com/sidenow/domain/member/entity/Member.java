package com.sidenow.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.board.entity.FreeBoardLike;
import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.boardType.free.comment.entity.FreeBoardCommentLike;
import com.sidenow.domain.member.constant.MemberConstant.Provider;
import com.sidenow.domain.member.constant.MemberConstant.Role;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
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

    // 게시글 작성자가 삭제되면 게시글도 삭제됨
    @JsonIgnore // 무한 순환 참조 방지
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FreeBoard> freeBoards = new ArrayList<>();

    // 유저가 삭제되면 게시글 좋아요도 삭제됨
    @JsonIgnore // 무한 순환 참조 방지
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FreeBoardLike> freeBoardLikes = new ArrayList<>();

    // 댓글 작성자가 제거되면 댓글도 제거됨
    @JsonIgnore // 무한 순환 참조 방지
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FreeBoardComment> freeBoardComments = new ArrayList<>();

    // 유저가 삭제되면 댓글 좋아요도 삭제됨
    @JsonIgnore // 무한 순환 참조 방지
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FreeBoardCommentLike> freeBoardCommentLikes = new ArrayList<>();

    // 유저 로그인 시간 업데이트
    public void updateLoginAt(LocalDateTime now) {
        this.loginAt = now;
    }

    // 유저 닉네임 변경
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}

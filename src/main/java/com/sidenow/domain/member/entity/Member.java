package com.sidenow.domain.member.entity;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.member.constant.MemberConstant.Provider;
import com.sidenow.domain.member.constant.MemberConstant.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

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
    private String address;

    // 작성한 자유게시판 게시글
    @OneToMany(mappedBy = "member")
    private List<FreeBoard> freeBoards = new ArrayList<>();

    // 작성한 자유게시판 게시글의 댓글
    @OneToMany(mappedBy = "member")
    private List<FreeBoardComment> freeBoardComments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private boolean isDeleted;
    private String reasonToLeave;

//    public void setMember(String password, String nickname, String name, String address) {
//        this.password = password;
//        this.nickname = nickname;
//        this.name = name;
//        this.address = address;
//    }
//
//    public void setDeleted(String reasonToLeave) {
//        this.isDeleted = true;
//        this.reasonToLeave = reasonToLeave;
//    }
}

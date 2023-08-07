package com.sidenow.domain.member.entity;

import com.sidenow.domain.member.constant.MemberConstant.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(unique = true, nullable = false, length = 30)
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

//    @Lob
//    @Column(nullable = false)
//    private String idImageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isDeleted;
    private String reasonToLeave;

//    @Builder
//    public Member(Long memberId, String email, String password, String name, String nickname, String address, Role role){
//        this.memberId = memberId;
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.nickname = nickname;
//        this.address = address;
//        this.role = role;
//    }

//    @Builder
//    public Member(String email, Role role){
//        this.email = email;
//        this.role = role;
//    }

    public void setMember(String password, String nickname, String name, String address) {
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.address = address;
    }

    public void setDeleted(String reasonToLeave) {
        this.isDeleted = true;
        this.reasonToLeave = reasonToLeave;
    }
}

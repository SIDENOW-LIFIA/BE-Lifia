package com.sidenow.domain.member;

import com.sidenow.domain.member.constant.MemberConstant.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    @Column(length = 10, nullable = false)
    private String name;

    @Column(unique = true, length = 20, nullable = false)
    private String nickname;

    @Column(nullable = false, length = 50)
    private String address;

    @Lob
    @Column(nullable = false)
    private String idImageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String reasonToLeave;

    @Builder
    public Member(Long memberId, String email, String nickname, String password, String name, String address){
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = Role.Role_USER;
    }
}

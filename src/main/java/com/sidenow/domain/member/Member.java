package com.sidenow.domain.member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Member(Long id, String email, String nickname, String password, String name, String address){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = Role.USER;
    }
}

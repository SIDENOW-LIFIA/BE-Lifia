package com.sidenow.domain.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 30)
    private String email;

    @Column(unique = true, length = 20)
    private String nickname;

    @Column(length = 20)
    private String password;

    @Column(length = 10)
    private String name;

    @Column(nullable = false, length = 50)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(Long id, String email, String nickname, String password, String name, String address, Role role){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
    }
}

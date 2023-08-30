package com.sidenow.utils;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.member.entity.Member;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.sidenow.domain.member.constant.MemberConstant.*;

@Data
public class MockMember extends Member {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private Long memberId = 10000L;
    private String email = "test1234@test.com";
    private String password = "test1234@";
    private String name = "testName";
    private String nickname = "testNickname";
    private String address = "testAddress";
    private Provider provider = Provider.KAKAO;
    private Role role = Role.Role_USER;

    private LocalDate createdAt = LocalDate.now();
    private LocalDate loginAt = LocalDate.now();
    private List<FreeBoard> freeBoards = new ArrayList<>();
    private List<FreeBoardComment> freeBoardComments = new ArrayList<>();

    // 유저 로그인 시간 업데이트
    public void updateLoginAt(LocalDate now) {
        this.loginAt = now;
    }

    // 유저 닉네임 변경
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}

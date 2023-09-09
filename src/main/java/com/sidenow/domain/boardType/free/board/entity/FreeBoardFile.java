package com.sidenow.domain.boardType.free.board.entity;

import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class FreeBoardFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long freeBoardFileId;

    private String originFileName;
    private String newFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_post_id")
    private FreeBoard freeBoard;

    @Builder
    public FreeBoardFile(String originFileName, String newFileName, Member member, FreeBoard freeBoard) {
        super();
        this.originFileName = originFileName;
        this.newFileName = newFileName;
        this.member = member;
        this.freeBoard = freeBoard;
    }

    public void updateImg(String originFileName, String newFileName) {
        this.originFileName = originFileName;
        this.newFileName = newFileName;
    }
}

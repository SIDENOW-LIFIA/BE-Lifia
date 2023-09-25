package com.sidenow.domain.boardType.vote.board.entity;

import com.sidenow.domain.boardType.vote.comment.entity.VoteBoardComment;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class VoteBoardFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteBoardFileId;

    private String originFileName;
    private String newFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_board_post_id")
    private VoteBoard voteBoard;

    @Builder
    public VoteBoardFile(String originFileName, String newFileName, Member member, VoteBoard voteBoard) {
        super();
        this.originFileName = originFileName;
        this.newFileName = newFileName;
        this.member = member;
        this.voteBoard = voteBoard;
    }

    public void updateImg(String originFileName, String newFileName) {
        this.originFileName = originFileName;
        this.newFileName = newFileName;
    }
}

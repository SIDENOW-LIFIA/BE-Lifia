package com.sidenow.domain.boardType.childCare.board.entity;

import com.sidenow.domain.boardType.childCare.comment.entity.ChildCareBoardComment;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ChildCareBoardFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long childCareBoardFileId;

    private String originFileName;
    private String newFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "childCare_board_post_id")
    private ChildCareBoard childCareBoard;

    @Builder
    public ChildCareBoardFile(String originFileName, String newFileName, Member member, ChildCareBoard childCareBoard) {
        super();
        this.originFileName = originFileName;
        this.newFileName = newFileName;
        this.member = member;
        this.childCareBoard = childCareBoard;
    }

    public void updateImg(String originFileName, String newFileName) {
        this.originFileName = originFileName;
        this.newFileName = newFileName;
    }
}

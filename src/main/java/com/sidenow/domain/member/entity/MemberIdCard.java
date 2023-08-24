package com.sidenow.domain.member.entity;

import com.sidenow.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.asm.ConvertDate;

@Getter
@NoArgsConstructor
@Entity
public class MemberIdCard extends BaseTimeEntity {

    private String originalFileName;
    private String newFileName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public MemberIdCard(String originalFileName, String newFileName, Member member) {
        super();
        this.originalFileName = originalFileName;
        this.newFileName = newFileName;
        this.member = member;
    }

    public void updateImg(String originalFileName, String newFileName) {
        this.originalFileName = originalFileName;
        this.newFileName = newFileName;
    }
}

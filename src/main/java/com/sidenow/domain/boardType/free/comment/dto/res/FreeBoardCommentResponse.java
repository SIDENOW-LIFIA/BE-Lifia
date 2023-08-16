package com.sidenow.domain.boardType.free.comment.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class FreeBoardCommentResponse {
    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "자유게시판 게시글의 댓글 전체 조회 응답 객체")
    public static class ReadFreeBoardCommentsAllResponse {
        private final int cnt; // 댓글 개수
        private final List<>
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "자유게시판 게시글의 댓글 상세 조회 응답 객체")
    public static class ReadFreeBoardCommentDetailResponse {
        private final String nickname;
        private final String content;
        private final boolean isDelete;
        private final String createdAt;
        private final Long parentId;
    }

}

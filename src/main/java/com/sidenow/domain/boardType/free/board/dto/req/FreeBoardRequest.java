package com.sidenow.domain.boardType.free.board.dto.req;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class FreeBoardRequest {

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 작성 요청 객체")
    public static class FreeBoardCreateRequest {
        @NotBlank(message = "자유게시판 게시글 제목 입력")
        @Schema(name = "게시글 제목", description = "게시글 제목을 입력해주세요.")
        private final String title;

        @NotBlank(message = "자유게시판 게시글 내용 입력")
        @Schema(name = "게시글 내용", description = "게시글 내용을 입력해주세요.")
        private final String content;

        private final LocalDateTime regDate;

        public static FreeBoard to(FreeBoardCreateRequest req, String image, Member member) {
            return FreeBoard.builder()
                    .title(req.title)
                    .content(req.content)
                    .image(image)
                    .member(member)
                    .regDate(req.getRegDate())
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 수정 요청 객체")
    public static class FreeBoardUpdateRequest {
        @NotBlank(message = "자유게시판 게시글 제목 입력")
        @Schema(description = "게시글 제목을 입력해주세요.")
        private final String title;

        @NotBlank(message = "자유게시판 게시글 내용 입력")
        @Schema(description = "게시글 내용을 입력해주세요.")
        private final String content;

        private final LocalDateTime regDate;


        public static FreeBoard to(FreeBoardUpdateRequest req, String image, Member member){
            return FreeBoard.builder()
                    .title(req.title)
                    .content(req.content)
                    .image(image)
                    .member(member)
                    .updatedDate(req.regDate)
                    .build();
        }
    }


}

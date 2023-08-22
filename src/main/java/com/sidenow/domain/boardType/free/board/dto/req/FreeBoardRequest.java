package com.sidenow.domain.boardType.free.board.dto.req;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public abstract class FreeBoardRequest {

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 등록 요청 객체")
    public static class CreateFreeBoardPostRequest {
        @NotBlank(message = "자유게시판 게시글 제목 입력")
        @Schema(description = "게시글 제목을 입력해주세요.")
        private final String title;

        @NotBlank(message = "자유게시판 게시글 내용 입력")
        @Schema(description = "게시글 내용을 입력해주세요.")
        private final String content;

        @Schema(description = "게시글에 이미지를 첨부해주세요.")
        private final String imageUrl;

        public static FreeBoard to(CreateFreeBoardPostRequest requestDto, Member member){
            return FreeBoard.builder()
                    .title(requestDto.title)
                    .content(requestDto.content)
                    .imageUrl(requestDto.imageUrl)
                    .member(member)
                    .build();
        }
    }
}

package com.sidenow.domain.boardType.free.board.dto;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public abstract class FreeBoardDto {

    @Getter
    @RequiredArgsConstructor
    public static class CreateFreeBoardRequest{
        @NotBlank(message = "자유게시판 게시글 제목 입력")
        @Schema(description = "게시글 제목을 입력해주세요.")
        private String title;

        @NotBlank(message = "자유게시판 게시글 내용 입력")
        @Schema(description = "게시글 내용을 입력해주세요.")
        private String content;

        @Schema(description = "게시글에 이미지를 첨부해주세요.")
        private String imageUrl;

        public static FreeBoard to(CreateFreeBoardRequest requestDto, Member member){
            return FreeBoard.builder()
                    .title(requestDto.title)
                    .content(requestDto.content)
                    .imageUrl(requestDto.imageUrl)
                    .writer(member)
                    .build();
        }
    }

    public static class FreeBoardUpdateRequest{

    }

    public static class FreeBoardDeleteRequest{

    }
}

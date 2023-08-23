package com.sidenow.domain.boardType.free.board.dto.req;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public abstract class FreeBoardRequest {

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "게시글 등록 요청 객체")
    public static class FreeBoardRegisterPostRequest {
        @NotBlank(message = "자유게시판 게시글 제목 입력")
        @Schema(description = "게시글 제목을 입력해주세요.")
        private final String title;

        @NotBlank(message = "자유게시판 게시글 내용 입력")
        @Schema(description = "게시글 내용을 입력해주세요.")
        private final String content;

        @Schema(description = "게시글 작성자 ID")
        private final Long memberId;

        @Schema(description = "게시글 첨부 파일")
        private final List<MultipartFile> files;

        public static FreeBoard to(FreeBoardRegisterPostRequest createFreeBoardPostRequest, Member member){
            return FreeBoard.builder()
                    .title(createFreeBoardPostRequest.title)
                    .content(createFreeBoardPostRequest.content)
                    .member(member)
                    .build();
        }
    }


}

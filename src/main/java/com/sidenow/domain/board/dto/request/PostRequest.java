package com.sidenow.domain.board.dto.request;

import com.sidenow.domain.board.entity.Board;
import com.sidenow.domain.board.entity.Post;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public abstract class PostRequest {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "게시글 작성 요청 객체")
    public static class PostCreateRequest {
        @NotBlank(message = "게시글 제목 입력")
        @Schema(description = "게시글 제목을 입력해주세요.", example = "Test Title")
        private String title;

        @NotBlank(message = "게시글 내용 입력")
        @Schema(description = "게시글 내용을 입력해주세요.", example = "Test Content")
        private String content;

        public static Post to(Board board, Member member, String image, PostCreateRequest request){

            return Post.builder()
                    .board(board)
                    .title(request.title)
                    .content(request.content)
                    .writer(member)
                    .image(image)
                    .build();
        }
    }
}

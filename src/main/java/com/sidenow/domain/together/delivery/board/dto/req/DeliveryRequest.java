package com.sidenow.domain.together.delivery.board.dto.req;

import com.sidenow.domain.together.delivery.board.entity.Delivery;
import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public abstract class DeliveryRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "게시글 작성 요청 객체")
    public static class DeliveryCreateRequest {

        @NotBlank(message = "배달해요 게시글 제목 입력")
        @Schema(description = "게시글 제목을 입력해주세요.", example = "Test Title")
        private String title;

        @NotBlank(message = "배달해요 게시글 내용 입력")
        @Schema(description = "게시글 내용을 입력해주세요.", example = "Test Content")
        private String content;

        public static Delivery to(DeliveryCreateRequest req, Member member, String image) {

            return Delivery.builder()
                    .title(req.title)
                    .content(req.content)
                    .member(member)
                    .image(image)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "게시글 수정 요청 객체")
    public static class DeliveryUpdateRequest {
        @NotBlank(message = "배달해요 게시글 제목 입력")
        @Schema(description = "게시글 제목을 입력해주세요.")
        private String title;

        @NotBlank(message = "배달해요 게시글 내용 입력")
        @Schema(description = "게시글 내용을 입력해주세요.")
        private String content;
    }
}

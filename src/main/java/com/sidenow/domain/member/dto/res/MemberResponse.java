package com.sidenow.domain.member.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


public abstract class MemberResponse {

    @Getter
    @Setter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "유저 생성, 삭제, 수정 응답 객체")
    public static class MemberCheck {
        @Schema(description = "유저 생성 여부 확인")
        private boolean saved;

        @Schema(description = "유저 삭제 여부 확인")
        private boolean deleted;

        @Schema(description = "유저 정보 수정 여부 확인")
        private boolean updated;
    }

}

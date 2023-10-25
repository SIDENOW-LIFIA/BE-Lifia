package com.sidenow.domain.together.childcare.application.dto.req;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.together.childcare.application.entity.ApplicationForm;
import com.sidenow.domain.together.childcare.board.entity.Childcare;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public abstract class ApplicationFormRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "육아해요 공동육아 신청폼 생성 요청 객체")
    public static class ApplicationFormCreateRequest {

        @NotBlank(message = "아파트 동, 호수를 입력하세요.")
        @Schema(description = "아파트 동, 호수 기입", example = "101동 209호")
        private String apartment;

        @NotBlank(message = "아이의 나이를 입력하세요.")
        @Schema(description = "아이 나이", example = "5")
        private int childAge;

        @NotBlank(message = "휴대폰 번호를 입력하세요.")
        @Schema(description = "휴대폰 번호", example = "010-1234-1234")
        private String phoneNumber;

        @Schema(description = "비고", example = "아이가 잘 울어요")
        private String note;

        public static ApplicationForm to(ApplicationFormCreateRequest req, Member member, Childcare childcare) {

            return ApplicationForm.builder()
                    .apartment(req.apartment)
                    .childAge(req.childAge)
                    .phoneNumber(req.phoneNumber)
                    .note(req.note)
                    .member(member)
                    .childcare(childcare)
                    .build();
        }
    }
}

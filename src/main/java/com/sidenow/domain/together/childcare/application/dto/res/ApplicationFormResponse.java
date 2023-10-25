package com.sidenow.domain.together.childcare.application.dto.res;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.together.childcare.application.entity.ApplicationForm;
import com.sidenow.domain.together.childcare.board.entity.Childcare;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public abstract class ApplicationFormResponse {

    @Data
    @AllArgsConstructor
    @Schema(description = "육아해요 공동육아 신청폼 생성 응답 객체")
    public static class ApplicationFormCreateResponse {
        private Long id;
        private Member applicant;

        public static ApplicationFormCreateResponse from(ApplicationForm applicationForm){
            return new ApplicationFormCreateResponse(applicationForm.getFormId(), applicationForm.getMember());
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "공동육아 신청폼 조회 응답 객체")
    public static class ApplicationFormGetResponse {
        private final Long id;
        private final String apartment;
        private final int childAge;
        private final String phoneNumber;
        private final String note;
        private final Childcare childcare;

        public static ApplicationFormGetResponse from(ApplicationForm applicationForm){

            return ApplicationFormGetResponse.builder()
                    .id(applicationForm.getFormId())
                    .apartment(applicationForm.getApartment())
                    .childAge(applicationForm.getChildAge())
                    .phoneNumber(applicationForm.getPhoneNumber())
                    .note(applicationForm.getNote())
                    .childcare(applicationForm.getChildcare())
                    .build();
        }
    }
}

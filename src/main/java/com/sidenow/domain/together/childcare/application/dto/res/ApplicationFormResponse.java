package com.sidenow.domain.together.childcare.application.dto.res;

import com.sidenow.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

public abstract class ApplicationResponse {

    @Data
    @AllArgsConstructor
    @Schema(description = "육아해요 공동육아 신청폼 생성 응답 객체")
    public static class ApplicationCreateResponse {
        private Long id;
        private Member applicant;
        private String apartment;
        
    }
}

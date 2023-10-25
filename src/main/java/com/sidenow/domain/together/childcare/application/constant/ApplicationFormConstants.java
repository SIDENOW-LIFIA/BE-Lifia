package com.sidenow.domain.together.childcare.application.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ApplicationFormConstants {
    @Getter
    @RequiredArgsConstructor
    public enum ApplicationFormResponseMessage {
        CREATE_APPLICATION_FORM_SUCCESS("육아해요 게시글이 등록되었습니다."),
        UPDATE_APPLICATION_FORM_SUCCESS("육아해요 게시글이 수정되었습니다"),
        GET_APPLICATION_FORM_SUCCESS("육아해요 게시글을 상세 조회하였습니다."),
        GET_APPLICATION_FORM_LIST_SUCCESS("육아해요 게시글을 전체 조회하였습니다.");
        private final String message;
    }
}

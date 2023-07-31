package com.sidenow.common;

import lombok.Getter;

@Getter
public class ResponseDto {
    private String message;
    public ResponseDto(String message) {
        this.message = message;
    }
}

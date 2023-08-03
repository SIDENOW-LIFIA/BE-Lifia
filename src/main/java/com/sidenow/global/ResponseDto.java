package com.sidenow.global;

import lombok.Getter;

@Getter
public class ResponseDto {
    private String message;
    public ResponseDto(String message) {
        this.message = message;
    }
}

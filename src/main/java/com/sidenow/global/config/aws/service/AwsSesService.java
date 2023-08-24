package com.sidenow.global.config.aws.service;

public interface AwsSesService {
    String send(String receiver);
    boolean verifyEmailCode(String email, String code);
}

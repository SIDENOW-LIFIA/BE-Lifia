package com.sidenow.global.config.aws;

public interface AwsSesService {
    void send(String receiver);
    void verifyCode(String code);
    String getCode();
}

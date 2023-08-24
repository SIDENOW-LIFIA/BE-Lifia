package com.sidenow.global.config.aws;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwsSesServiceImpl implements AwsSesService{

    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private String code;

    @Override
    public void send(String receiver){


    }

    @Override
    public void verifyCode(String code){

    }

    @Override
    public String getCode(){

    }
}

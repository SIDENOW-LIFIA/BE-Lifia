//package com.sidenow.global.config.aws;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//@Getter
//@NoArgsConstructor
//public class AwsProperty {
//
//    @Value("${cloud.aws.credentials.accessKey}")
//    private String accessKey;
//
//    @Value("${cloud.aws.credentials.secretKey}")
//    private String secretKey;
//
//    @Value("${cloud.aws.region.static}")
//    private String region;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    public AwsProperty(String accessKey, String secretKey, String bucket, String region) {
//        this.accessKey = accessKey;
//        this.secretKey = secretKey;
//        this.bucket = bucket;
//        this.region = region;
//    }
//}

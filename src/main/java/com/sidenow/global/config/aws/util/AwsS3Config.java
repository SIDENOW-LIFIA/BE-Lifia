package com.sidenow.global.config.aws.util;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
public class AwsS3Config {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Bean
    @Primary
    public BasicAWSCredentials awsCredentialsProvider() {
        log.info("S3 Configuration: Aws Credentials Provider 진입");
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        log.info("S3 Configuration: Aws Credentials Provider 종료");

        return basicAWSCredentials;
    }

    @Bean
    public AmazonS3 amazonS3() {
        log.info("S3 Configuration: Amazon S3 진입");
        AmazonS3 s3Builder = AmazonS3ClientBuilder.standard()
                        .withRegion(region)
                        .withCredentials(new AWSStaticCredentialsProvider(awsCredentialsProvider()))
                        .build();
        log.info("S3 Configuration: Amazon S3 종료");

        return s3Builder;
    }

    @Bean
    public AmazonS3Client amazonS3Client() {
        log.info("S3 Configuration: Amazon S3 Client 진입");
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        log.info("S3 Configuration: Amazon S3 Client 종료");

        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }
}

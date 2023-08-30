package com.sidenow.global.config.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private AmazonS3 s3Client;
    private final AwsProperty awsProperty;

    @PostConstruct
    public void setUp() {

        AWSCredentials credentials = new BasicAWSCredentials(awsProperty.getAccessKey(), awsProperty.getSecretKey());
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsProperty.getRegion())
                .build();
    }

    public String uploadFile(MultipartFile file, String filePath) {

        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IllegalStateException("Filename Null");
        }
        String extension = fileName.split("\\.")[1].toLowerCase();
        if (extension.contains(".jpg")){
            extension = extension.replace(".jpg", ".jpeg");
        }
        fileName = UUID.randomUUID().toString()+"."+extension;
        try {
            s3Client.putObject(new PutObjectRequest(awsProperty.getBucket(), filePath + fileName, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return s3Client.getUrl(awsProperty.getBucket(), filePath + fileName).toString();
        } catch (Exception e) {
            throw new IllegalStateException("Image Send Error");
        }
    }
}

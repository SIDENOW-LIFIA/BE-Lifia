package com.sidenow.global.config.aws.service;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {

    String uploadFile(MultipartFile image);
    void deleteFile(String fileName);
    String createFileName(String fileName);
    String getFileExtension(String fileName);
    String getFilePath(String newFileName);
}

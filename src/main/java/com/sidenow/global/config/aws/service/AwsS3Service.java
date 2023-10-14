package com.sidenow.global.config.aws.service;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AwsS3Service {

    String uploadFile(MultipartFile image);
    void deleteFile(String fileName);
    String createFileName(String fileName);
    String getFileExtension(String fileName);
    String getFilePath(String newFileName);
}

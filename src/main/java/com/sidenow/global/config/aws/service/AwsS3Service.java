package com.sidenow.global.config.aws.service;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AwsS3Service {

    List<String> uploadFile(Member member, FreeBoard freeBoard, List<MultipartFile> multipartFile);
    void deleteFreeBoardFile(FreeBoard freeBoard);
    String createFileName(String fileName);
    String getFileExtension(String fileName);
    String getFilePath(String newFileName);
}

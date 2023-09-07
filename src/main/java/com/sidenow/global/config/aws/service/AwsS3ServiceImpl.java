package com.sidenow.global.config.aws.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.board.entity.FreeBoardFile;
import com.sidenow.domain.boardType.free.board.repository.FreeBoardFileRepository;
import com.sidenow.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AwsS3ServiceImpl implements AwsS3Service{

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;
    private final FreeBoardFileRepository freeBoardFileRepository;

    @Override
    public List<String> uploadFile(Member member, FreeBoard freeBoard, List<MultipartFile> multipartFile){

        List<String> fileNameList = new ArrayList<>();
        multipartFile.forEach(file -> {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }

            FreeBoardFile freeBoardFile = FreeBoardFile.builder()
                    .newFileName(fileName)
                    .originFileName(file.getOriginalFilename())
                    .member(member)
                    .freeBoard(freeBoard)
                    .build();

            freeBoardFileRepository.save(freeBoardFile);

            fileNameList.add(fileName);
        });
        return fileNameList;
    }

    @Override
    public void deleteFreeBoardFile(FreeBoard freeBoard){
        List<FreeBoardFile> freeBoardFiles = freeBoardFileRepository.findByFreeBoard(freeBoard);

        freeBoardFiles.forEach(freeBoardFile -> {
            freeBoardFileRepository.delete(freeBoardFile);
        });
    }

    @Override
    public String createFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    @Override
    public String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ")입니다.");
        }
    }

    @Override
    public String getFilePath(String newFileName){
        return amazonS3Client.getResourceUrl(bucket, newFileName);
    }
}

package com.example.usedTrade.file.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    /**
     * 파일저장후 저장된 파일이름 반환
     */
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension; // uuid.png(저장될 파일이름)
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;    // Users/.../uuid.png

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);    // 파일저장
        fos.close();

        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        if (deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    };
}

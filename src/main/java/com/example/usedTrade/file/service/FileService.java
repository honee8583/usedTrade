package com.example.usedTrade.file.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    @Value("${tradeImageLocation}")
    private String tradeImageLocation;

    /**
     * 파일저장후 저장된 파일이름 반환
     */
    public String uploadFile(String originalFileName, byte[] fileData) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid + extension; // uuid.png(저장될 파일이름)
        String folderPath = makeFolder();

        String fileUploadFullUrl = tradeImageLocation + folderPath + "/" + savedFileName;    // Users/.../uuid.png
        log.info("FileUploadFullUrl : " + fileUploadFullUrl);   // TODO

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);    // 파일저장
        fos.close();

        log.info(folderPath + "/" + savedFileName);

        return folderPath + "/" + savedFileName;   // /2022/04/23/uuid.png
    }

    /**
     * 파일 삭제
     */
    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(tradeImageLocation + filePath);

        if (deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }

    private String makeFolder() {
         String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

         String folderPath = str.replace("/", File.separator);
         log.info("폴더경로: " + folderPath);   // TODO

         File uploadPathFolder = new File(tradeImageLocation, folderPath);

         if (!uploadPathFolder.exists()) {
             uploadPathFolder.mkdirs();
         }

         return folderPath;
    }
}
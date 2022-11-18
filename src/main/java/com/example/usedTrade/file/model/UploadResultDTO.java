package com.example.usedTrade.file.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO {
    private String fileName;
    private String uuid;
    private String folderPath;

    public String getImageUrl() {
        try{
            return URLEncoder
                    .encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}

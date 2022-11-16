package com.example.usedTrade.trade.service.impl;

import com.example.usedTrade.file.service.FileService;
import com.example.usedTrade.trade.entity.TradeImage;
import com.example.usedTrade.trade.repository.TradeImageRepository;
import com.example.usedTrade.trade.service.TradeImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeImageServiceImpl implements TradeImageService {

    @Value("${tradeImgLocation}")
    private String tradeImgLocation;

    private final TradeImageRepository tradeImageRepository;

    private final FileService fileService;

    @Override
    public void saveTradeImage(TradeImage tradeImage, MultipartFile multipartFile) throws Exception {
        String originalImageName = multipartFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(originalImageName)) {
            log.info(originalImageName);
            imgName = fileService.uploadFile(tradeImgLocation, originalImageName, multipartFile.getBytes());
            imgUrl = "/images/trade/" + imgName;
        }

        tradeImage.updateTradeImg(originalImageName, imgName, imgUrl);
        tradeImageRepository.save(tradeImage);
    }

    @Override
    public void updateTradeImage(Long tradeImgId, MultipartFile multipartFile) throws Exception {
        if (!multipartFile.isEmpty()) {
            TradeImage savedTradeImage = tradeImageRepository.findById(tradeImgId)
                    .orElseThrow(() -> new RuntimeException("이미지가 존재하지 않습니다."));  //TODO CustomException

            // 파일 삭제
            if (!StringUtils.isEmpty(savedTradeImage.getImgName())) {
                fileService.deleteFile(tradeImgLocation + "/" + savedTradeImage.getImgName());
            }

            // 파일 새로 업로드
            String oriImgName = multipartFile.getOriginalFilename();
            String imgName =
                    fileService.uploadFile(tradeImgLocation, oriImgName, multipartFile.getBytes());
            String imgUrl = "/images/trade/" + imgName;
            savedTradeImage.updateTradeImg(oriImgName, imgName, imgUrl);

            // 파일이미지 데이터베이스 수정
            tradeImageRepository.save(savedTradeImage);
        }
    }
}

package com.example.usedTrade.trade.service;

import com.example.usedTrade.trade.entity.TradeImage;
import org.springframework.web.multipart.MultipartFile;

public interface TradeImageService {
    void saveTradeImage(TradeImage tradeImage, MultipartFile multipartFile) throws Exception;
    void updateTradeImage(Long tradeImgId, MultipartFile multipartFile) throws Exception;
}

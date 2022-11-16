package com.example.usedTrade.trade.service;

import com.example.usedTrade.page.PageRequestDTO;
import com.example.usedTrade.page.PageResultDTO;
import com.example.usedTrade.trade.model.TradeDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TradeService {
    /**
     * 거래글 등록
     */
    void register(TradeDto tradeDto, List<MultipartFile> multipartFileList) throws Exception;

    /**
     * 거래글 수정
     */
    void modify(TradeDto tradeDto, List<MultipartFile> multipartFileList) throws Exception;

    /**
     * 거래글 삭제
     */
    void delete(long tradeId);

    /**
     * 거래글 조회
     */
    TradeDto getTrade(long tradeId);

    /**
     * 거래글 전체 조회
     */
    PageResultDTO getTradeList(PageRequestDTO pageRequestDTO);
}

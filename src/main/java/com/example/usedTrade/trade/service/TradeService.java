package com.example.usedTrade.trade.service;

import com.example.usedTrade.trade.model.TradeDto;
import com.example.usedTrade.trade.model.TradeInput;

import java.util.List;

public interface TradeService {
    /**
     * 거래글 등록
     */
    void register(TradeInput tradeInput);

    /**
     * 거래글 수정
     */
    void modify(long tradeId, TradeInput tradeInput);

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
    List<TradeDto> getTradeList();
}

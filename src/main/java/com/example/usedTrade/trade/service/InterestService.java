package com.example.usedTrade.trade.service;

import com.example.usedTrade.trade.model.InterestInput;

public interface InterestService {

    /**
     * 좋아요 한 게시글인지 체크
     */
    boolean get(long tradeId, String email);

    /**
     * 좋아요 등록
     */
    void add(long tradeId, String email);

    /**
     * 좋아요 해제
     */
    void delete(long tradeId, String email);
}

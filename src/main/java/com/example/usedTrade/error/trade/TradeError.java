package com.example.usedTrade.error.trade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TradeError {
    TRADE_NOT_FOUND("거래 게시글이 존재하지 않습니다.");

    private final String description;
}

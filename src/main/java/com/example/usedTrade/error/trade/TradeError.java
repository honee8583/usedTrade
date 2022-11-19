package com.example.usedTrade.error.trade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TradeError {
    TRADE_NOT_FOUND("거래 게시글이 존재하지 않습니다."),
    NOT_INTERESTED_TRADE("좋아요한 게시글이 아닙니다."),
    ALREADY_INTERESTED_TRADE("이미 좋아요한 게시글입니다.");

    private final String description;
}

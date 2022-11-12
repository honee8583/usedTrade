package com.example.usedTrade.error.keyword;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeywordError {
    TRADE_ALREADY_EXISTS("이미 존재하는 키워드입니다."),
    TRADE_NOT_EXISTS("존재하지 않는 키워드입니다.");

    private final String description;
}

package com.example.usedTrade.trade.model;

import com.example.usedTrade.keyword.entity.Keyword;
import com.example.usedTrade.trade.entity.Trade;
import com.example.usedTrade.trade.entity.TradeStatus;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TradeDto {
    private long id;
    private String email;
    private String title;
    private String content;
    private int price;
    private List<String> keywordList = new ArrayList<>();
    private TradeStatus tradeStatus;
    private LocalDateTime regDt;
    private LocalDateTime upDt;

    public static TradeDto entityToDto(Trade trade) {
        TradeDto dto = TradeDto.builder()
                .id(trade.getId())
                .email(trade.getMember().getEmail())
                .title(trade.getTitle())
                .content(trade.getContent())
                .price(trade.getPrice())
                .tradeStatus(trade.getTradeStatus())
                .regDt(trade.getRegDt())
                .upDt(trade.getUpDt())
                .build();
        dto.keywordList = new ArrayList<>(trade.getKeywordList());

        return dto;
    }
}

package com.example.usedTrade.trade.model;

import com.example.usedTrade.trade.entity.Trade;
import com.example.usedTrade.trade.entity.TradeStatus;
import lombok.*;

import java.time.LocalDateTime;

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
    private String keyword;
    private TradeStatus tradeStatus;
    private LocalDateTime regDt;
    private LocalDateTime upDt;

    public static TradeDto entityToDto(Trade trade) {
        return TradeDto.builder()
                .id(trade.getId())
                .email(trade.getMember().getEmail())
                .title(trade.getTitle())
                .content(trade.getContent())
                .price(trade.getPrice())
                .keyword(trade.getKeyword())
                .tradeStatus(trade.getTradeStatus())
                .regDt(trade.getRegDt())
                .upDt(trade.getUpDt())
                .build();
    }
}

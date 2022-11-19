package com.example.usedTrade.trade.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InterestInput {
    private long tradeId;
    private String email;
}

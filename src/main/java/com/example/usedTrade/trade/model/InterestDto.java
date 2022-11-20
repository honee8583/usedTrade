package com.example.usedTrade.trade.model;

import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.trade.entity.Interest;
import com.example.usedTrade.trade.entity.Trade;
import lombok.*;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InterestDto {

    private long tradeId;
    private String title;
    private long price;
    private LocalDateTime regDt;
    private List<String> keywordList;

    private String email;

    public static InterestDto entityToDto(Interest interest) {
        Trade trade = interest.getTrade();
        Member member = interest.getMember();

        InterestDto dto =  InterestDto.builder()
                .tradeId(trade.getId())
                .title(trade.getTitle())
                .price(trade.getPrice())
                .regDt(trade.getRegDt())
                .email(member.getEmail())
                .build();
        dto.keywordList = new ArrayList<>(trade.getKeywordList());

        return dto;
    }
}

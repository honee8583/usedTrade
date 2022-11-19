package com.example.usedTrade.trade.entity;

import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.trade.model.TradeDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "Trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    private TradeStatus tradeStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<String> keywordList = new HashSet<>();

    private String title;
    private String content;
    private int price;

    private LocalDateTime regDt;
    private LocalDateTime upDt;

    public void modifyTrade(TradeDto tradeDto) {
        this.tradeStatus = TradeStatus.valueOf(tradeDto.getTradeStatus());
        this.title = tradeDto.getTitle();
        this.content = tradeDto.getContent();
        this.price = tradeDto.getPrice();
        this.upDt = LocalDateTime.now();
        this.keywordList = new HashSet<>();

        for (String k: tradeDto.getKeywordList()) {
            this.keywordList.add(k);
        }
    }

    public void addKeyword(String keyword) {
        keywordList.add(keyword);
    }
}

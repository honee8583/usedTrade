package com.example.usedTrade.trade.entity;

import com.example.usedTrade.keyword.entity.Keyword;
import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.trade.model.TradeInput;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
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
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    private TradeStatus tradeStatus;

    // TODO
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<String> keywordList = new HashSet<>();

    private String title;
    private String content;
    private int price;

    private LocalDateTime regDt;
    private LocalDateTime upDt;

    public void modifyTrade(TradeInput tradeInput) {
        this.tradeStatus = TradeStatus.valueOf(tradeInput.getTradeStatus());
        this.title = tradeInput.getTitle();
        this.content = tradeInput.getContent();
        this.price = tradeInput.getPrice();
        this.upDt = LocalDateTime.now();
        this.keywordList = new HashSet<>();

        for (String k: tradeInput.getKeywordList()) {
            this.keywordList.add(k);
        }
    }

    public void addKeyword(String keyword) {
        keywordList.add(keyword);
    }
}

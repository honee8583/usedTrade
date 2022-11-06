package com.example.usedTrade.trade.entity;

import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.trade.model.TradeInput;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.validator.internal.util.ReflectionHelper.typeOf;

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

    private String keyword;
    private String title;
    private String content;
    private int price;

    private LocalDateTime regDt;
    private LocalDateTime upDt;

    public void modifyTrade(TradeInput tradeInput) {
        this.tradeStatus = TradeStatus.valueOf(tradeInput.getTradeStatus());
        this.keyword = tradeInput.getKeyword();
        this.title = tradeInput.getTitle();
        this.content = tradeInput.getContent();
        this.price = tradeInput.getPrice();
        this.upDt = LocalDateTime.now();
    }
}

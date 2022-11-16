package com.example.usedTrade.trade.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "trade")
@Entity
public class TradeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String imgName;

    private String originalImgName;

    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Trade trade;

    public void updateTradeImg(String originalImgName, String imgName, String imgUrl) {
        this.originalImgName = originalImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}

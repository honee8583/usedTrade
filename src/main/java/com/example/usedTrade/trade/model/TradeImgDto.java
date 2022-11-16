package com.example.usedTrade.trade.model;

import com.example.usedTrade.trade.entity.TradeImage;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TradeImgDto {

    private Long id;
    private String imgName;
    private String originalImgName;
    private String imgUrl;

    public static TradeImgDto entityToDto(TradeImage tradeImage) {
        return TradeImgDto.builder()
                .id(tradeImage.getId())
                .imgName(tradeImage.getImgName())
                .originalImgName(tradeImage.getOriginalImgName())
                .imgUrl(tradeImage.getImgUrl())
                .build();
    }
}

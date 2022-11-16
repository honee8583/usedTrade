package com.example.usedTrade.trade.model;

import com.example.usedTrade.trade.entity.Trade;
import com.example.usedTrade.trade.entity.TradeStatus;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;
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
    private Long id;

    private long idx;

    private String email;

    @NotBlank(message = "제목은 필수 입력 사항입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 사항입니다.")
    private String content;

    @NotNull(message = "가격은 필수 입력 사항입니다.")
    @Min(value = 0, message = "가격은 최소 0원입니다.")
    @Max(value = 100000000, message = "가격은 최대 100000000원입니다.")
    private int price;

    @NotEmpty(message = "판매여부는 필수 입력 사항입니다.")
    private String tradeStatus;

    @NotNull(message = "키워드는 필수 입력 사항입니다.")
    private List<String> keywordList;

    private List<TradeImgDto> tradeImgDtoList = new ArrayList<>();

    private List<Long> tradeImgIds = new ArrayList<>();

    private LocalDateTime regDt;

    private LocalDateTime upDt;

    public static TradeDto entityToDto(Trade trade) {
        TradeDto dto = TradeDto.builder()
                .id(trade.getId())
                .email(trade.getMember().getEmail())
                .title(trade.getTitle())
                .content(trade.getContent())
                .price(trade.getPrice())
                .tradeStatus(String.valueOf(trade.getTradeStatus()))
                .regDt(trade.getRegDt())
                .upDt(trade.getUpDt())
                .build();
        dto.keywordList = new ArrayList<>(trade.getKeywordList());

        return dto;
    }
}

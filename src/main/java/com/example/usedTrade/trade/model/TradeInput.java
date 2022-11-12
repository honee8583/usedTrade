package com.example.usedTrade.trade.model;

import com.example.usedTrade.trade.entity.TradeStatus;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TradeInput {
    private long id;
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

    @NotEmpty(message = "키워드는 필수 입력 사항입니다.")
    private List<String> keywordList;
}

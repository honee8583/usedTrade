package com.example.usedTrade.keyword.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class KeywordInput {

    @NotEmpty(message = "키워드이름은 필수 입력값입니다.")
    private String keywordName;

}

package com.example.usedTrade.keyword.model;

import com.example.usedTrade.keyword.entity.Keyword;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class KeywordDto {
    private long id;
    private String keywordName;

    public static KeywordDto entityToDto(Keyword keyword) {
        return KeywordDto.builder()
                            .id(keyword.getId())
                            .keywordName(keyword.getKeywordName())
                            .build();
    }
}

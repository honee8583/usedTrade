package com.example.usedTrade.keyword.entity;

import com.example.usedTrade.keyword.model.KeywordInput;
import com.example.usedTrade.trade.entity.Trade;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "Keyword")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String keywordName;

    public void modifyKeyword(KeywordInput keywordInput) {
        this.keywordName = keywordInput.getKeywordName();
    }
}

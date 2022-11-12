package com.example.usedTrade.keyword.repository;

import com.example.usedTrade.keyword.entity.Keyword;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class KeywordRepositoryTest {

    @Autowired
    KeywordRepository keywordRepository;

    @Test
    void testFindByKeywordName() {
        // given
        String keywordName = "생활용품";
        keywordRepository.save(Keyword.builder().keywordName(keywordName).build());

        // when
        Optional<Keyword> optionalKeyword = keywordRepository.findByKeywordName(keywordName);

        // then
        assertEquals(keywordName, optionalKeyword.get().getKeywordName());
    }
}
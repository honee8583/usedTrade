package com.example.usedTrade.keyword.service;

import com.example.usedTrade.keyword.entity.Keyword;
import com.example.usedTrade.keyword.model.KeywordDto;
import com.example.usedTrade.keyword.model.KeywordInput;
import com.example.usedTrade.keyword.repository.KeywordRepository;
import com.example.usedTrade.keyword.service.impl.KeywordServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@Transactional
@ExtendWith(MockitoExtension.class)
class KeywordServiceTest {

    @Mock
    KeywordRepository keywordRepository;

    @InjectMocks
    KeywordServiceImpl keywordService;

    private static final String KEYWORDNAME1 = "생활용품";
    private static final String KEYWORDNAME2 = "전자기기";

    private Keyword createKeyword() {
        String keywordName = "생활용품";
        return Keyword.builder().id(2L).keywordName(keywordName).build();
    }

    private KeywordInput createKeywordInput() {
        return KeywordInput.builder()
                .keywordName(KEYWORDNAME1)
                .build();
    }

    @Test
    void testCreateKeyword() {
        // given
        KeywordInput keywordInput = createKeywordInput();

        Keyword keyword = createKeyword();

        given(keywordRepository.findByKeywordName(anyString()))
                .willReturn(Optional.of(keyword));

        // when
        keywordService.create(keywordInput);

        // then
        verify(keywordRepository).save(any());
    }

    @Test
    void testGet() {
        // given
        Keyword keyword = createKeyword();

        given(keywordRepository.findById(anyLong()))
                .willReturn(Optional.of(keyword));

        // when
        KeywordDto dto = keywordService.get(2L);

        // then
        assertEquals(keyword.getId(), dto.getId());
        assertEquals(keyword.getKeywordName(), dto.getKeywordName());
    }

    @Test
    void testModify() {
        // given
        Keyword keyword = createKeyword();
        KeywordInput input = createKeywordInput();
        input.setKeywordName(KEYWORDNAME2);

        given(keywordRepository.findById(anyLong()))
                .willReturn(Optional.of(keyword));

        // when
        keywordService.modify(2L, input);

        // then
        verify(keywordRepository).save(any());
    }

    @Test
    void testDelete() {
        // given
        Keyword keyword = createKeyword();

        given(keywordRepository.findById(anyLong()))
                .willReturn(Optional.of(keyword));

        // when
        keywordService.delete(2L);

        // then
        verify(keywordRepository).deleteById(anyLong());
    }

    @Test
    void testGetKeywordList() {
        // given
        Keyword keyword1 = createKeyword();
        Keyword keyword2 = Keyword.builder().id(2L).keywordName("전자기기").build();
        List<Keyword> keywordList = Arrays.asList(keyword1, keyword2);

        given(keywordRepository.findAll()).willReturn(keywordList);

        // when
        List<KeywordDto> keywordDtoList = keywordService.getKeywordList();

        // then
        assertEquals(2, keywordDtoList.size());
    }
}
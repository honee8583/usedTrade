package com.example.usedTrade.keyword.service.impl;

import com.example.usedTrade.error.keyword.exception.KeywordAlreadyExistsException;
import com.example.usedTrade.error.keyword.exception.KeywordNotFoundException;
import com.example.usedTrade.keyword.entity.Keyword;
import com.example.usedTrade.keyword.model.KeywordDto;
import com.example.usedTrade.keyword.model.KeywordInput;
import com.example.usedTrade.keyword.repository.KeywordRepository;
import com.example.usedTrade.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;

    @Override
    public void create(KeywordInput keywordInput) {
        log.info(keywordInput.toString());

        Optional<Keyword> optionalKeyword =
                keywordRepository.findByKeywordName(keywordInput.getKeywordName());
        if (optionalKeyword.isPresent()) {
            throw new KeywordAlreadyExistsException();
        }

        Keyword keyword = Keyword.builder()
                            .keywordName(keywordInput.getKeywordName())
                            .build();

        keywordRepository.save(keyword);
    }

    @Override
    public KeywordDto get(long keywordId) {
        log.info("get keyword : " + keywordId);

        Optional<Keyword> optionalKeyword = keywordRepository.findById(keywordId);
        if (!optionalKeyword.isPresent()) {
            throw new KeywordNotFoundException();
        }

        return KeywordDto.entityToDto(optionalKeyword.get());
    }

    @Override
    public void modify(long keywordId, KeywordInput keywordInput) {
        log.info("modify keyword: " + keywordInput);

        Keyword keyword = keywordRepository.findById(keywordId)
                .orElseThrow(KeywordNotFoundException::new);

        keyword.modifyKeyword(keywordInput);

        keywordRepository.save(keyword);
    }

    @Override
    public void delete(long keywordId) {
        log.info("delete keyword: " + keywordId);

        keywordRepository.findById(keywordId)
                .orElseThrow(KeywordNotFoundException::new);

        keywordRepository.deleteById(keywordId);
    }

    @Override
    public List<KeywordDto> getKeywordList() {
        log.info("Get Keywords");

        List<Keyword> keywords = keywordRepository.findAll();

        return keywords.stream().map(KeywordDto::entityToDto)
                .collect(Collectors.toList());
    }
}

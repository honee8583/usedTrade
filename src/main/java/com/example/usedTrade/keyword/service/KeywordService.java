package com.example.usedTrade.keyword.service;

import com.example.usedTrade.keyword.entity.Keyword;
import com.example.usedTrade.keyword.model.KeywordDto;
import com.example.usedTrade.keyword.model.KeywordInput;

import java.util.List;

public interface KeywordService {
    /**
     * 키워드 생성
     */
    void create(KeywordInput keywordInput);

    /**
     * 특정 키워드 불로오기
     */
    KeywordDto get(long keywordId);

    /**
     * 키워드 수정
     */
    void modify(long keywordId, KeywordInput keywordInput);

    /**
     * 키워드 삭제
     */
    void delete(long keywordId);

    /**
     * 모든 키워드 불러오기
     */
    List<KeywordDto> getKeywordList();
}

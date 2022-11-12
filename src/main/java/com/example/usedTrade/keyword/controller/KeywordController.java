package com.example.usedTrade.keyword.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/keyword")
@RequiredArgsConstructor
public class KeywordController {

    @GetMapping("/create.do")
    public String createKeywordPage() {

        return "keyword/create";
    }

    @GetMapping("/list.do")
    public String getKeywordListPage() {

        return "keyword/list";
    }

    @GetMapping("/modify.do")
    public String updateKeywordPage(long keywordId) {

        return "keyword/modify";
    }

}

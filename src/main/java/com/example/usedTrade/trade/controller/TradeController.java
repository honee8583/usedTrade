package com.example.usedTrade.trade.controller;

import com.example.usedTrade.keyword.service.KeywordService;
import com.example.usedTrade.trade.entity.TradeStatus;
import com.example.usedTrade.trade.model.TradeInput;
import com.example.usedTrade.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/trade")
public class TradeController {

    private final TradeService tradeService;
    private final KeywordService keywordService;

    @GetMapping("/register")
    public String register(Model model) {

//        model.addAttribute("keywordList", keywordService.getKeywordList());
        model.addAttribute(new TradeInput());

        return "trade/register";
    }

    @GetMapping("/list")
    public String getList() {

        return "trade/list";
    }

    @GetMapping("/detail")
    public String detail(Model model, long tradeId, Principal principal) {
        log.info("detail : " + tradeId);

        model.addAttribute("username", principal.getName());

        return "trade/detail";
    }

    @GetMapping("/modify")
    public String modify(long tradeId) {
        log.info("modifying : " + tradeId);

        return "trade/modify";
    }

}

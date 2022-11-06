package com.example.usedTrade.trade.controller;

import com.example.usedTrade.trade.model.TradeInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trade")
public class TradeController {

    @GetMapping("/register")
    public String register(Model model) {

//        model.addAttribute("keywordList", keywordService.getKeywordList());
        model.addAttribute(new TradeInput());

        return "trade/register";
    }

}

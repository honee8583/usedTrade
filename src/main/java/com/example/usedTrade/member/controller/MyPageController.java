package com.example.usedTrade.member.controller;

import com.example.usedTrade.page.PageRequestDTO;
import com.example.usedTrade.page.PageResultDTO;
import com.example.usedTrade.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final TradeService tradeService;

    @GetMapping("/list")
    public String getList(Model model,
                          @ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO,
                          Principal principal) {

        log.info("list PageRequestDto: " + pageRequestDTO.toString());

        pageRequestDTO.setType("e");
        pageRequestDTO.setKeyword(principal.getName());
        PageResultDTO pageResultDTO =
                tradeService.getTradeList(pageRequestDTO);

        model.addAttribute("pageResultDTO", pageResultDTO);

        return "member/myTradeList";
    }

    @GetMapping("/interestList")
    public String getRepliedList(Model model, @ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO,
                                 Principal principal) {

        pageRequestDTO.setType("e");
        pageRequestDTO.setKeyword(principal.getName());
        PageResultDTO pageResultDTO
                = tradeService.getMyInterestTradeList(pageRequestDTO);

        model.addAttribute("pageResultDTO", pageResultDTO);

        return "member/myInterestList";
    }
}

package com.example.usedTrade.trade.controller;

import com.example.usedTrade.keyword.service.KeywordService;
import com.example.usedTrade.page.PageRequestDTO;
import com.example.usedTrade.page.PageResultDTO;
import com.example.usedTrade.trade.model.TradeDto;
import com.example.usedTrade.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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

        log.info("Trade Register");

        model.addAttribute("keywordList", keywordService.getKeywordList());
        model.addAttribute(new TradeDto());

        return "trade/register";
    }

    @PostMapping("/register")
    public String registerSubmit(Model model, @Valid TradeDto tradeDto,
                                 BindingResult bindingResult,
                                 Principal principal) {

        log.info("Trade registerSubmit: " + tradeDto);

        if (bindingResult.hasErrors()) {
            return "trade/register";
        }

        try {
            tradeDto.setEmail(principal.getName());
            tradeService.register(tradeDto);
        } catch (IllegalStateException e) {
            model.addAttribute("message", e.getMessage());
            return "trade/register";
        }

        return "redirect:/trade/list";
    }

    @GetMapping("/list")
    public String getList(Model model, PageRequestDTO pageRequestDTO) {
        log.info("list PageRequestDto: " + pageRequestDTO.toString());

        PageResultDTO pageResultDTO =
                tradeService.getTradeList(pageRequestDTO);

        model.addAttribute("pageResultDTO", pageResultDTO);

        return "trade/list";
    }

    @GetMapping("/detail")
    public String detail(Model model, long tradeId, Principal principal) {
        log.info("detail : " + tradeId);

        TradeDto tradeDto = tradeService.getTrade(tradeId);
        model.addAttribute("tradeDto", tradeDto);
        model.addAttribute("username" , principal.getName());

        return "trade/detail";
    }

    @GetMapping("/modify")
    public String modify(Model model, long tradeId) {
        log.info("modifying : " + tradeId);

        TradeDto tradeDto = tradeService.getTrade(tradeId);
        model.addAttribute("tradeDto", tradeDto);

        return "trade/register";
    }

    @PostMapping("/modify")
    public String modifySubmit(@Valid TradeDto tradeDto) {

        log.info("trade modifySubmit: " + tradeDto);

        tradeService.modify(tradeDto.getId(), tradeDto);

        return "redirect:/trade/detail?tradeId=" + tradeDto.getId();
    }

    @PostMapping("/delete")
    public String deleteSubmit(long tradeId) {
        log.info("trade deleteSubmit: " + tradeId);

        tradeService.delete(tradeId);

        return "redirect:/trade/list";
    }

}

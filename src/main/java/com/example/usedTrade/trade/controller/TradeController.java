package com.example.usedTrade.trade.controller;

import com.example.usedTrade.error.AbstractException;
import com.example.usedTrade.keyword.service.KeywordService;
import com.example.usedTrade.page.PageRequestDTO;
import com.example.usedTrade.page.PageResultDTO;
import com.example.usedTrade.trade.model.TradeDto;
import com.example.usedTrade.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

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
                                 @RequestParam("tradeImgFile") List<MultipartFile> multipartFileList,
                                 Principal principal) {

        log.info("Trade registerSubmit: " + tradeDto);

        if (bindingResult.hasErrors()) {
            return "trade/register";
        }

        if (multipartFileList.get(0).isEmpty() && tradeDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력값입니다.");
            return "trade/register";
        }

        try {
            tradeDto.setEmail(principal.getName());
            tradeService.register(tradeDto, multipartFileList);
        } catch (Exception e) {
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

        try{
            TradeDto tradeDto = tradeService.getTrade(tradeId);
            log.info(tradeDto.toString());
            model.addAttribute("tradeDto", tradeDto);
            model.addAttribute("username" , principal.getName());
        } catch(AbstractException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("tradeDto", new TradeDto());
            return "trade/list";
        }

        return "trade/detail";
    }

    @GetMapping("/modify")
    public String modify(Model model, long tradeId) {
        log.info("modifying : " + tradeId);

        try{
            TradeDto tradeDto = tradeService.getTrade(tradeId);
            model.addAttribute("tradeDto", tradeDto);
        } catch (AbstractException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "trade/list";
        }

        return "trade/register";
    }

    @PostMapping("/modify")
    public String modifySubmit(Model model, @Valid TradeDto tradeDto,
                               BindingResult bindingResult,
                               @RequestParam("tradeImgFile") List<MultipartFile> multipartFileList) {

        log.info("trade modifySubmit: " + tradeDto);

        if (bindingResult.hasErrors()) {
            return "trade/register";
        }

        if (multipartFileList.get(0).isEmpty() && tradeDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력값입니다.");
            return "trade/register";
        }

        try{
            tradeService.modify(tradeDto, multipartFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "수정 중 에러가 발생하였습니다.");
            return "trade/register";
        }

        return "redirect:/trade/detail?tradeId=" + tradeDto.getId();
    }

    @PostMapping("/delete")
    public String deleteSubmit(long tradeId) {
        log.info("trade deleteSubmit: " + tradeId);

        tradeService.delete(tradeId);

        return "redirect:/trade/list";
    }

}

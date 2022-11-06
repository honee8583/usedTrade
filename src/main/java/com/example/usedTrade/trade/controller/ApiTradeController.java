package com.example.usedTrade.trade.controller;

import com.example.usedTrade.trade.model.TradeDto;
import com.example.usedTrade.trade.model.TradeInput;
import com.example.usedTrade.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade")
public class ApiTradeController {

    private final TradeService tradeService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid TradeInput tradeInput, Principal principal) {

        log.info("tradeInput: " + tradeInput);

        tradeInput.setEmail(principal.getName());
        tradeService.register(tradeInput);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}

package com.example.usedTrade.trade.controller;

import com.example.usedTrade.page.PageRequestDTO;
import com.example.usedTrade.page.PageResultDTO;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade")
public class ApiTradeController {

    private final TradeService tradeService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid TradeInput tradeInput,
                                      Principal principal) {

        log.info("tradeInput: " + tradeInput);

        tradeInput.setEmail(principal.getName());
        tradeService.register(tradeInput);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResultDTO> getList(@RequestParam("page") int page) {
        log.info("api getTradeList page: " + page);

        PageResultDTO pageResultDTO =
                tradeService.getTradeList(new PageRequestDTO(page, 5));

        return new ResponseEntity<>(pageResultDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TradeDto> detail(@PathVariable long id) {
        TradeDto tradeDto = tradeService.getTrade(id);

        return new ResponseEntity<>(tradeDto, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Boolean> update(@RequestBody @Valid TradeInput tradeInput,
                                          @PathVariable long id) {
        log.info("modify input: " + tradeInput);
        tradeService.modify(id, tradeInput);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id) {
        tradeService.delete(id);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}

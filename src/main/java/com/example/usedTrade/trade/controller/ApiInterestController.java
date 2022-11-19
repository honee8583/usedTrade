package com.example.usedTrade.trade.controller;

import com.example.usedTrade.trade.service.InterestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/interest")
@RequiredArgsConstructor
public class ApiInterestController {

    private final InterestService interestService;

    @GetMapping("/{id}")
    public ResponseEntity<Boolean> getInterest(@PathVariable("id") long tradeId, Principal principal) {

        log.info("interest input : " + tradeId);

        boolean interest = interestService.get(tradeId, principal.getName());

        return new ResponseEntity<>(interest, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Boolean> addInterest(@PathVariable("id") long tradeId,
                                               Principal principal) {

        log.info("interest input: " + tradeId);

        interestService.add(tradeId, principal.getName());

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteInterest(@PathVariable("id") long tradeId,
                                                  Principal principal) {

        log.info("delete interest : " + tradeId);

        interestService.delete(tradeId, principal.getName());

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}

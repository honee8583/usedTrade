package com.example.usedTrade.trade.service.impl;

import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.trade.entity.Trade;
import com.example.usedTrade.trade.entity.TradeStatus;
import com.example.usedTrade.trade.model.TradeDto;
import com.example.usedTrade.trade.model.TradeInput;
import com.example.usedTrade.trade.repository.TradeRepository;
import com.example.usedTrade.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    @Override
    public void register(TradeInput tradeInput) {
        Trade trade = Trade.builder()
                .member(Member.builder().email(tradeInput.getEmail()).build())
                .title(tradeInput.getTitle())
                .content(tradeInput.getContent())
                .price(tradeInput.getPrice())
                .tradeStatus(TradeStatus.valueOf(tradeInput.getTradeStatus()))
                .keyword(tradeInput.getKeyword())
                .regDt(LocalDateTime.now())
                .build();
        tradeRepository.save(trade);
    }

    @Override
    public void modify(TradeInput tradeInput) {
        Trade trade = findTrade(tradeInput.getId());
        trade.modifyTrade(tradeInput);
        tradeRepository.save(trade);
    }

    @Override
    public void delete(long tradeId) {
        findTrade(tradeId);
        tradeRepository.deleteById(tradeId);
    }

    @Override
    @Transactional(readOnly = true)
    public TradeDto getTrade(long tradeId) {
        Trade trade = findTrade(tradeId);

        return TradeDto.entityToDto(trade);
    }

    @Override
    public List<TradeDto> getTradeList() {
        List<Trade> tradeList = tradeRepository.findAll();

        return tradeList.stream().map(TradeDto::entityToDto).collect(Collectors.toList());
    }

    private Trade findTrade(long tradeId) {
        return tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
    }
}

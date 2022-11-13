package com.example.usedTrade.trade.service.impl;

import com.example.usedTrade.error.keyword.exception.KeywordNotFoundException;
import com.example.usedTrade.error.trade.exception.TradeNotFoundException;
import com.example.usedTrade.keyword.entity.Keyword;
import com.example.usedTrade.keyword.repository.KeywordRepository;
import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.page.PageRequestDTO;
import com.example.usedTrade.page.PageResultDTO;
import com.example.usedTrade.trade.entity.Trade;
import com.example.usedTrade.trade.entity.TradeStatus;
import com.example.usedTrade.trade.model.TradeDto;
import com.example.usedTrade.trade.model.TradeInput;
import com.example.usedTrade.trade.repository.TradeRepository;
import com.example.usedTrade.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final KeywordRepository keywordRepository;

    @Override
    public void register(TradeInput tradeInput) {
        Trade trade = Trade.builder()
                .member(Member.builder().email(tradeInput.getEmail()).build())
                .title(tradeInput.getTitle())
                .content(tradeInput.getContent())
                .price(tradeInput.getPrice())
                .tradeStatus(TradeStatus.valueOf(tradeInput.getTradeStatus()))
                .regDt(LocalDateTime.now())
                .build();

        for (String s: tradeInput.getKeywordList()) {
            Keyword keyword = keywordRepository.findByKeywordName(s)
                            .orElseThrow(KeywordNotFoundException::new);
            trade.addKeyword(s);
        }

        tradeRepository.save(trade);
    }

    @Override
    public void modify(long tradeId, TradeInput tradeInput) {
        Trade trade = findTrade(tradeId);
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
    @Transactional(readOnly = true)
    public PageResultDTO<TradeDto, Trade> getTradeList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable();

        Page<Trade> tradeList = tradeRepository.findAllByOrderByRegDtDesc(pageable);

        Function<Trade, TradeDto> fn = TradeDto::entityToDto;

        int totalTradeCount = (int) tradeRepository.countAll();

        PageResultDTO<TradeDto, Trade> pageResultDTO = new PageResultDTO<>(tradeList, fn);

        int size = pageResultDTO.getSize();
        int page = pageResultDTO.getPage();
        int elements = pageResultDTO.getDtoList().size();

        int idx = 0;
        for (int i = totalTradeCount - (page - 1) * size; i > totalTradeCount - size * page; i--) {
            pageResultDTO.getDtoList().get(idx++).setIdx(i);
            if (i <= 1) {
                break;
            }
        }

        return pageResultDTO;
    }

    private Trade findTrade(long tradeId) {
        return tradeRepository.findById(tradeId)
                .orElseThrow(TradeNotFoundException::new);
    }
}

package com.example.usedTrade.trade.service;

import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.member.repository.MemberRepository;
import com.example.usedTrade.trade.entity.Trade;
import com.example.usedTrade.trade.entity.TradeStatus;
import com.example.usedTrade.trade.model.TradeDto;
import com.example.usedTrade.trade.model.TradeInput;
import com.example.usedTrade.trade.repository.TradeRepository;
import com.example.usedTrade.trade.service.impl.TradeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    TradeRepository tradeRepository;

    @InjectMocks
    TradeServiceImpl tradeService;

    @Test
    void testRegister() {
        // given
        TradeInput tradeInput = TradeInput.builder()
                .title("title")
                .content("content")
                .price(1000)
                .tradeStatus("SELL")
                .keyword("생활용품")
                .build();

        // when
        tradeService.register(tradeInput);

        // then
        verify(tradeRepository).save(any());
    }

    @Test
    void testModify() {
        // given
        TradeInput modifyInput = TradeInput.builder()
                .title("title2")
                .content("content2")
                .price(10000)
                .tradeStatus("BUY")
                .keyword("가전기기")
                .build();

        Trade trade = Trade.builder()
                .id(2L)
                .title("title")
                .content("content")
                .price(1000)
                .tradeStatus(TradeStatus.valueOf("SELL"))
                .keyword("생활용품")
                .build();

        given(tradeRepository.findById(anyLong()))
                .willReturn(Optional.of(trade));

        // when
        tradeService.modify(modifyInput);
        Optional<Trade> optionalTrade = tradeRepository.findById(2L);

        // then
        verify(tradeRepository).save(any());
        assertEquals("title2", optionalTrade.get().getTitle());
    }

    @Test
    void testDelete() {
        // given
        Trade trade = Trade.builder()
                .id(2L)
                .title("title")
                .content("content")
                .price(1000)
                .tradeStatus(TradeStatus.valueOf("SELL"))
                .keyword("생활용품")
                .build();

        given(tradeRepository.findById(anyLong()))
                .willReturn(Optional.of(trade));

        // when
        tradeService.delete(2L);

        // then
        verify(tradeRepository).deleteById(anyLong());
    }

    @Test
    void testGetTrade() {
        // given
        Trade trade = Trade.builder()
                .id(2L)
                .member(Member.builder().email("test").build())
                .title("title")
                .content("content")
                .price(1000)
                .tradeStatus(TradeStatus.valueOf("SELL"))
                .keyword("생활용품")
                .build();

        given(tradeRepository.findById(anyLong()))
                .willReturn(Optional.of(trade));

        // when
        TradeDto tradeDto = tradeService.getTrade(2L);

        // then
        assertNotNull(tradeDto);
        assertEquals("title", tradeDto.getTitle());
        assertEquals("content", tradeDto.getContent());
        assertEquals(1000, tradeDto.getPrice());
        assertEquals(TradeStatus.SELL, tradeDto.getTradeStatus());
        assertEquals("생활용품", tradeDto.getKeyword());
    }

    @Test
    void testGetTradeList() {
        // given
        Trade trade = Trade.builder()
                .id(2L)
                .member(Member.builder().email("test").build())
                .title("title")
                .content("content")
                .price(1000)
                .tradeStatus(TradeStatus.valueOf("SELL"))
                .keyword("생활용품")
                .build();

        Trade trade2 = Trade.builder()
                .id(3L)
                .member(Member.builder().email("test").build())
                .title("title")
                .content("content")
                .price(1000)
                .tradeStatus(TradeStatus.valueOf("SELL"))
                .keyword("생활용품")
                .build();

        given(tradeRepository.findAll())
                .willReturn(new ArrayList<>(Arrays.asList(trade, trade2)));

        // when
        List<TradeDto> tradeList = tradeService.getTradeList();

        // then
        assertEquals(2, tradeList.size());
    }
}
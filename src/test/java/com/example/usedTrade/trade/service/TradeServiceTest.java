package com.example.usedTrade.trade.service;

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
import com.example.usedTrade.trade.service.impl.TradeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    TradeRepository tradeRepository;

    @Mock
    KeywordRepository keywordRepository;

    @InjectMocks
    TradeServiceImpl tradeService;

    @Test
    void testRegister() {
        // given
        List<String> keywordList = Arrays.asList("생활용품", "전자기기");
        TradeInput tradeInput = TradeInput.builder()
                .title("title")
                .content("content")
                .price(1000)
                .tradeStatus("SELL")
                .keywordList(keywordList)
                .build();

        Keyword keyword1 = Keyword.builder().keywordName("생활용품").build();
        Keyword keyword2 = Keyword.builder().keywordName("전자기기").build();

        keywordRepository.save(keyword1);
        keywordRepository.save(keyword2);

        given(keywordRepository.findByKeywordName(anyString())).willReturn(Optional.of(keyword1));
        given(keywordRepository.findByKeywordName(anyString())).willReturn(Optional.of(keyword2));

        // when
        tradeService.register(tradeInput);
        keywordRepository.findByKeywordName("생활용품");
        keywordRepository.findByKeywordName("전자기기");

        // then
        verify(tradeRepository).save(any());
    }

    @Test
    void testModify() {
        // given
        List<String> keywordList = Arrays.asList("생활용품", "전자기기");
        TradeInput modifyInput = TradeInput.builder()
                .title("title2")
                .content("content2")
                .price(1000)
                .tradeStatus("SELL")
                .keywordList(keywordList)
                .build();


        Set<String> keywordSet = new HashSet<>();
        keywordSet.add("생활용품");
        keywordSet.add("전자기기");
        Trade trade = Trade.builder()
                .id(2L)
                .title("title")
                .content("content")
                .price(1000)
                .tradeStatus(TradeStatus.valueOf("SELL"))
                .keywordList(keywordSet)
                .build();

        keywordRepository.save(Keyword.builder().keywordName("생활용품").build());
        keywordRepository.save(Keyword.builder().keywordName("전자기기").build());

        given(tradeRepository.findById(anyLong()))
                .willReturn(Optional.of(trade));

        // when
        tradeService.modify(2L, modifyInput);
        Optional<Trade> optionalTrade = tradeRepository.findById(2L);

        // then
        verify(tradeRepository).save(any());
        assertEquals("title2", optionalTrade.get().getTitle());
    }

    @Test
    void testDelete() {
        // given
        Set<String> keywordSet = new HashSet<>();
        keywordSet.add("생활용품");
        keywordSet.add("전자기기");
        Trade trade = Trade.builder()
                .id(2L)
                .title("title")
                .content("content")
                .price(1000)
                .tradeStatus(TradeStatus.valueOf("SELL"))
                .keywordList(keywordSet)
                .build();

        keywordRepository.save(Keyword.builder().keywordName("생활용품").build());
        keywordRepository.save(Keyword.builder().keywordName("전자기기").build());

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
        Set<String> keywordSet = new HashSet<>();
        keywordSet.add("생활용품");
        keywordSet.add("전자기기");
        Trade trade = Trade.builder()
                .id(2L)
                .title("title")
                .member(Member.builder().email("test@test.com").build())
                .content("content")
                .price(1000)
                .tradeStatus(TradeStatus.valueOf("SELL"))
                .keywordList(keywordSet)
                .build();

        keywordRepository.save(Keyword.builder().keywordName("생활용품").build());
        keywordRepository.save(Keyword.builder().keywordName("전자기기").build());

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
        assertTrue(tradeDto.getKeywordList().contains("생활용품"));
        assertTrue(tradeDto.getKeywordList().contains("전자기기"));
    }

    @Test
    void testGetTradeList() {
        // given
        Set<String> keywordSet = new HashSet<>();
        keywordSet.add("생활용품");
        keywordSet.add("전자기기");

        Trade trade = Trade.builder()
                .id(2L)
                .member(Member.builder().email("test@test.com").build())
                .title("title")
                .content("content")
                .price(1000)
                .tradeStatus(TradeStatus.valueOf("SELL"))
                .keywordList(keywordSet)
                .regDt(LocalDateTime.now()) // 먼저 생성된 게시글
                .build();


        Trade trade2 = Trade.builder()
                .id(3L)
                .member(Member.builder().email("test").build())
                .title("title")
                .content("content")
                .price(1000)
                .tradeStatus(TradeStatus.valueOf("SELL"))
                .keywordList(keywordSet)
                .regDt(LocalDateTime.now().plusDays(1)) // 더 나중에 생성된 거래글
                .build();

        PageRequestDTO pageRequestDTO = new PageRequestDTO();   // page: 1, size: 10

        keywordRepository.save(Keyword.builder().keywordName("생활용품").build());
        keywordRepository.save(Keyword.builder().keywordName("전자기기").build());

        List<Trade> tradeList = Arrays.asList(trade, trade2);

        given(tradeRepository.findAllByOrderByRegDtDesc(any()))
                .willReturn(new PageImpl<>(tradeList, pageRequestDTO.getPageable(), tradeList.size()));

        // when
        PageResultDTO pageResultDTO = tradeService.getTradeList(pageRequestDTO);

        // then
        assertEquals(2, pageResultDTO.getDtoList().size());
    }
}
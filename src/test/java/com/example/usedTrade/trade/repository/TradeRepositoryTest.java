package com.example.usedTrade.trade.repository;

import com.example.usedTrade.keyword.repository.KeywordRepository;
import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.member.repository.MemberRepository;
import com.example.usedTrade.trade.entity.Trade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TradeRepositoryTest {

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    KeywordRepository keywordRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void testFindAllByOrderByRegDtDesc() {
        // given
        Trade trade = new Trade();
        Trade trade2 = new Trade();

        tradeRepository.save(trade);
        tradeRepository.save(trade2);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Trade> tradePageList = tradeRepository.findAllByOrderByRegDtDesc(pageable);

        // then
        assertEquals(tradePageList.getContent().size(), 2);
    }

    @Test
    void countAll() {
        // given
        Trade trade = new Trade();
        Trade trade2 = new Trade();

        tradeRepository.save(trade);
        tradeRepository.save(trade2);

        // when
        long cnt = tradeRepository.countAll();

        // then
        assertEquals(2L, cnt);
    }

    @Test
    void testFindByEmail() {
        // given
        Member member = Member.builder().email("email").build();
        Member savedMember = memberRepository.save(member);

        Trade trade = Trade.builder().member(savedMember).build();
        Trade trade2 = Trade.builder().member(savedMember).build();

        tradeRepository.save(trade);
        tradeRepository.save(trade2);

        // when
        List<Trade> tradeList = tradeRepository.findByMember(savedMember);

        // then
        assertEquals(tradeList.size(), 2);
    }
}
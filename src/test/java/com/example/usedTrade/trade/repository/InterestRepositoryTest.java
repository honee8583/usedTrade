package com.example.usedTrade.trade.repository;

import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.member.entity.MemberRole;
import com.example.usedTrade.member.repository.MemberRepository;
import com.example.usedTrade.trade.entity.Interest;
import com.example.usedTrade.trade.entity.Trade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InterestRepositoryTest {

    @Autowired
    InterestRepository interestRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void testFindByTrade() {
        // given
        Member member = Member.builder().email("email").build();
        Trade trade = Trade.builder().build();

        Member savedMember = memberRepository.save(member);
        Trade savedTrade = tradeRepository.save(trade);

        Interest interest = Interest.builder()
                            .trade(savedTrade)
                            .member(savedMember)
                            .build();

        interestRepository.save(interest);

        // when
        Optional<Interest> optional = interestRepository.findByTrade(savedTrade);

        // then
        assertNotNull(optional);
    }

    @Test
    void testFindByMember() {
        // given
        Member member = Member.builder().email("email").build();
        Trade trade = Trade.builder().build();

        Member savedMember = memberRepository.save(member);
        Trade savedTrade = tradeRepository.save(trade);

        Interest interest = Interest.builder()
                .trade(savedTrade)
                .member(savedMember)
                .build();

        interestRepository.save(interest);

        // when
        Optional<Interest> optional = interestRepository.findByMember(savedMember);

        // then
        assertNotNull(optional);
    }

    @Test
    void testFindByTrade_idAndMember_email() {
        // given
        Member member = Member.builder().email("email").build();
        Trade trade = Trade.builder().build();

        Member savedMember = memberRepository.save(member);
        Trade savedTrade = tradeRepository.save(trade);

        Interest interest = Interest.builder()
                .trade(savedTrade)
                .member(savedMember)
                .build();

        interestRepository.save(interest);

        // when
        Optional<Interest> optional = interestRepository.findByTrade_idAndMember_email(savedTrade.getId(), savedMember.getEmail());

        // then
        Interest savedInterest = optional.get();
        assertNotNull(optional);
        assertEquals(savedInterest.getMember().getEmail(), savedMember.getEmail());
        assertEquals(savedInterest.getTrade().getId(), savedTrade.getId());
    }
}
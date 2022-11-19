package com.example.usedTrade.trade.repository;

import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.trade.entity.Interest;
import com.example.usedTrade.trade.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    Optional<Interest> findByTrade(Trade trade);
    Optional<Interest> findByMember(Member member);
    Optional<Interest> findByTrade_idAndMember_email(long tradeId, String email);
}

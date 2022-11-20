package com.example.usedTrade.trade.repository;

import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.trade.entity.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long>, QuerydslPredicateExecutor<Trade> {
    Page<Trade> findAllByOrderByRegDtDesc(Pageable pageable);

    @Query(value = "select count(id) from Trade")
    long countAll();

    List<Trade> findByMember(Member member);
}

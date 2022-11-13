package com.example.usedTrade.trade.repository;

import com.example.usedTrade.trade.entity.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    Page<Trade> findAllByOrderByRegDtDesc(Pageable pageable);

    @Query(value = "select count(id) from Trade")
    long countAll();
}

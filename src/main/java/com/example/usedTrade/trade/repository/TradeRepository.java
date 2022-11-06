package com.example.usedTrade.trade.repository;

import com.example.usedTrade.trade.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}

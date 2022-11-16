package com.example.usedTrade.trade.repository;

import com.example.usedTrade.trade.entity.TradeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeImageRepository extends JpaRepository<TradeImage, Long> {
    List<TradeImage> findByTradeIdOrderByIdAsc(Long tradeId);
}

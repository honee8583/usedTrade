package com.example.usedTrade.reply.repository;

import com.example.usedTrade.reply.entity.Reply;
import com.example.usedTrade.trade.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByTrade_id(long tradeId);
}

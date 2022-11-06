package com.example.usedTrade.keyword.repository;

import com.example.usedTrade.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}

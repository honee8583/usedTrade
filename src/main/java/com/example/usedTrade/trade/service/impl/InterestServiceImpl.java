package com.example.usedTrade.trade.service.impl;

import com.example.usedTrade.error.trade.exception.AlreadyInterestedException;
import com.example.usedTrade.error.trade.exception.NotInterestedException;
import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.trade.entity.Interest;
import com.example.usedTrade.trade.entity.Trade;
import com.example.usedTrade.trade.model.InterestInput;
import com.example.usedTrade.trade.repository.InterestRepository;
import com.example.usedTrade.trade.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService {

    private final InterestRepository interestRepository;

    @Override
    public boolean get(long tradeId, String email) {
        Optional<Interest> optionalInterest =
                findInterest(tradeId, email);

        return optionalInterest.isPresent();
    }

    @Override
    public void add(long tradeId, String email) {
        Optional<Interest> optionalInterest = findInterest(tradeId, email);
        if (optionalInterest.isPresent()) {
            throw new AlreadyInterestedException();
        }

        interestRepository.save(Interest.builder()
                            .trade(Trade.builder().id(tradeId).build())
                            .member(Member.builder().email(email).build())
                            .build());
    }

    @Override
    public void delete(long tradeId, String email) {
        Interest interest = interestRepository
                .findByTrade_idAndMember_email(tradeId, email)
                .orElseThrow(NotInterestedException::new);

        interestRepository.delete(interest);
    }

    private Optional<Interest> findInterest(long tradeId, String email) {
        return interestRepository
                .findByTrade_idAndMember_email(tradeId, email);
    }
}

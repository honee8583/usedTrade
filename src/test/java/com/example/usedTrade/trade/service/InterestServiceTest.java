package com.example.usedTrade.trade.service;

import com.example.usedTrade.error.trade.exception.AlreadyInterestedException;
import com.example.usedTrade.error.trade.exception.NotInterestedException;
import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.trade.entity.Interest;
import com.example.usedTrade.trade.entity.Trade;
import com.example.usedTrade.trade.model.InterestInput;
import com.example.usedTrade.trade.repository.InterestRepository;
import com.example.usedTrade.trade.service.impl.InterestServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InterestServiceTest {

    @Mock
    InterestRepository interestRepository;

    @InjectMocks
    InterestServiceImpl interestService;

    @Test
    void testGet() {
        // given
        long tradeId = 2L;
        String email = "test@test.com";
//        InterestInput input = InterestInput.builder()
//                                .tradeId(tradeId)
//                                .email(email)
//                                .build();

        Interest interest = Interest.builder()
                .trade(Trade.builder().id(tradeId).build())
                .member(Member.builder().email(email).build())
                .build();

        given(interestRepository.findByTrade_idAndMember_email(anyLong(), anyString()))
                .willReturn(Optional.of(interest));

        // when
        boolean result = interestService.get(tradeId, email);

        // then
        assertTrue(result);
    }

    @Test
    void testAdd() {
        // given
        long tradeId = 2L;
        String email = "test@test.com";
//        InterestInput input = InterestInput.builder()
//                .tradeId(tradeId)
//                .email(email)
//                .build();

        given(interestRepository.findByTrade_idAndMember_email(anyLong(), anyString()))
                .willReturn(Optional.empty());

        // when
        interestService.add(tradeId, email);

        // then
        verify(interestRepository).save(any());
    }

    @Test
    void testAddException() {
        // given
        long tradeId = 2L;
        String email = "test@test.com";
//        InterestInput input = InterestInput.builder()
//                .tradeId(tradeId)
//                .email(email)
//                .build();

        Interest interest = Interest.builder()
                .trade(Trade.builder().id(tradeId).build())
                .member(Member.builder().email(email).build())
                .build();

        given(interestRepository.findByTrade_idAndMember_email(anyLong(), anyString()))
                .willReturn(Optional.of(interest));

        // when
        AlreadyInterestedException alreadyInterestedException =
                assertThrows(AlreadyInterestedException.class,
                        () -> interestService.add(tradeId, email));

        // then
        assertEquals("이미 좋아요한 게시글입니다.", alreadyInterestedException.getMessage());
    }

    @Test
    void testDelete() {
        // given
        long tradeId = 2L;
        String email = "test@test.com";
//        InterestInput input = InterestInput.builder()
//                .tradeId(tradeId)
//                .email(email)
//                .build();

        Interest interest = Interest.builder()
                .trade(Trade.builder().id(tradeId).build())
                .member(Member.builder().email(email).build())
                .build();

        given(interestRepository.findByTrade_idAndMember_email(anyLong(), anyString()))
                .willReturn(Optional.of(interest));

        // when
        interestService.delete(tradeId, email);

        // then
        verify(interestRepository).delete(any());
    }

    @Test
    void testDeleteException() {
        // given
        long tradeId = 2L;
        String email = "test@test.com";
//        InterestInput input = InterestInput.builder()
//                .tradeId(tradeId)
//                .email(email)
//                .build();

        given(interestRepository.findByTrade_idAndMember_email(anyLong(), anyString()))
                .willReturn(Optional.empty());

        // when
        NotInterestedException notInterestedException =
                assertThrows(NotInterestedException.class,
                        () -> interestService.delete(tradeId, email));

        // then
        assertEquals("좋아요한 게시글이 아닙니다.", notInterestedException.getMessage());
    }
}
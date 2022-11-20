package com.example.usedTrade.reply.repository;

import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.member.repository.MemberRepository;
import com.example.usedTrade.reply.entity.Reply;
import com.example.usedTrade.trade.entity.Trade;
import com.example.usedTrade.trade.repository.TradeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReplyRepositoryTest {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void testSave() {
        // given
        Trade savedTrade = tradeRepository.save(Trade.builder().build());
        Member savedMember = memberRepository.save(Member.builder().email("test").build());

        Reply reply = Reply.builder()
                .trade(savedTrade)
                .member(savedMember)
                .content("content")
                .regDt(LocalDateTime.now())
                .build();

        // when
        Reply savedReply = replyRepository.save(reply);

        // then
        assertEquals(savedReply.getTrade().getId(), savedTrade.getId());
        assertEquals(savedReply.getMember().getEmail(), savedMember.getEmail());
        assertEquals(savedReply.getContent(), "content");
    }

    @Test
    void testFindByTrade_Id() {
        // given
        Trade savedTrade = tradeRepository.save(Trade.builder().build());
        Member savedMember = memberRepository.save(Member.builder().email("test").build());

        Reply reply = Reply.builder()
                .trade(savedTrade)
                .member(savedMember)
                .content("content")
                .regDt(LocalDateTime.now())
                .build();

        Reply reply2 = Reply.builder()
                .trade(savedTrade)
                .member(savedMember)
                .content("content")
                .regDt(LocalDateTime.now())
                .build();

        replyRepository.save(reply);
        replyRepository.save(reply2);

        // when
        List<Reply> replyList = replyRepository.findByTrade_id(savedTrade.getId());

        // then
        assertEquals(2, replyList.size());
    }

    @Test
    void testFindByMember_email() {
        // given
        Trade savedTrade = tradeRepository.save(Trade.builder().build());
        Member savedMember = memberRepository.save(Member.builder().email("test").build());

        Reply reply = Reply.builder()
                .trade(savedTrade)
                .member(savedMember)
                .content("content")
                .regDt(LocalDateTime.now())
                .build();

        Reply reply2 = Reply.builder()
                .trade(savedTrade)
                .member(savedMember)
                .content("content")
                .regDt(LocalDateTime.now())
                .build();

        replyRepository.save(reply);
        replyRepository.save(reply2);

        // when
        List<Reply> replyList = replyRepository.findByMember_email(savedMember.getEmail());

        // then
        assertEquals(2, replyList.size());
    }
}
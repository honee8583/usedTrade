package com.example.usedTrade.reply.service;

import com.example.usedTrade.error.reply.ReplyError;
import com.example.usedTrade.error.reply.exception.NotExistReplyException;
import com.example.usedTrade.error.reply.exception.NotReplyWriterException;
import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.reply.entity.Reply;
import com.example.usedTrade.reply.model.ReplyDto;
import com.example.usedTrade.reply.model.ReplyInput;
import com.example.usedTrade.reply.repository.ReplyRepository;
import com.example.usedTrade.reply.service.impl.ReplyServiceImpl;
import com.example.usedTrade.trade.entity.Trade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {

    @Mock
    ReplyRepository replyRepository;

    @InjectMocks
    ReplyServiceImpl replyService;

    private ReplyInput createInput(long tradeId, String email, String content) {
        return ReplyInput.builder()
                .trade_id(tradeId)
                .email(email)
                .content(content)
                .build();
    }

    private Reply createReply(long tradeId, String email, long replyId) {
        Trade trade = Trade.builder().id(tradeId).build();
        Member member = Member.builder().email(email).build();

        return Reply.builder()
                        .id(replyId)
                        .trade(trade)
                        .member(member)
                        .content("content")
                        .regDt(LocalDateTime.now())
                        .build();
    }

    @Test
    void testWrite() {
        // given
        Trade trade = Trade.builder().id(2L).build();
        Member member = Member.builder().email("email").build();

        ReplyInput input = createInput(trade.getId(), member.getEmail(), "create content");

        // when
        replyService.write(input);

        // then
        verify(replyRepository).save(any());
    }

    @Test
    void testGet() {
        // given
        long replyId = 2L;
        Reply reply = createReply(2L, "email", 2L);

        given(replyRepository.findById(anyLong()))
                .willReturn(Optional.of(reply));

        // when
        ReplyDto dto = replyService.get(replyId);

        // then
        assertEquals(dto.getId(), replyId);
        assertEquals(reply.getContent(), dto.getContent());
        assertEquals(reply.getMember().getEmail(), dto.getEmail());
        assertEquals(reply.getTrade().getId(), dto.getTrade_id());
    }

    @Test
    void testGetException() {
        // given
        long replyId = 2L;
        Reply reply = createReply(2L, "email", 2L);

        given(replyRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when
        NotExistReplyException notExistReplyException =
                assertThrows(NotExistReplyException.class,
                        () -> replyService.get(replyId));

        // then
        assertEquals(notExistReplyException.getMessage(), ReplyError.NOT_EXIST_REPLY.getDescription());
    }

    @Test
    void testGetReplyList() {
        // given
        long tradeId = 2L;

        Reply reply1 = createReply(tradeId, "email", 2L);
        Reply reply2 = createReply(tradeId, "email", 3L);

        given(replyRepository.findByTrade_id(anyLong()))
                .willReturn(Arrays.asList(reply1, reply2));

        // when
        List<ReplyDto> dtoList = replyService.getReplyList(tradeId);

        // then
        assertEquals(2, dtoList.size());
    }

    @Test
    void testModify() {
        // given
        Reply reply = createReply(2L, "email", 2L);

        ReplyInput modifyInput =
                createInput(2L, "email", "modified content");
        modifyInput.setReply_id(2L);

        given(replyRepository.findById(anyLong()))
                .willReturn(Optional.of(reply));

        // when
        replyService.modify(modifyInput);

        // then
        verify(replyRepository).save(any());
    }

    @Test
    @DisplayName("ModifyTest -> NotExistReplyException")
    void testModifyException() {
        ReplyInput modifyInput =
                createInput(2L, "email", "modified content");
        modifyInput.setReply_id(2L);

        given(replyRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when
        NotExistReplyException notExistReplyException =
                assertThrows(NotExistReplyException.class,
                        () -> replyService.modify(modifyInput));

        // then
        assertEquals(notExistReplyException.getMessage(), ReplyError.NOT_EXIST_REPLY.getDescription());
    }

    @Test
    @DisplayName("ModifyTest -> NotReplyWriterException")
    void testModifyException2() {
        ReplyInput modifyInput =
                createInput(2L, "email", "modified content");
        modifyInput.setReply_id(2L);

        Reply reply = createReply(2L, "email2", 2L);

        given(replyRepository.findById(anyLong()))
                .willReturn(Optional.of(reply));

        // when
        NotReplyWriterException notReplyWriterException =
                assertThrows(NotReplyWriterException.class,
                        () -> replyService.modify(modifyInput));

        // then
        assertEquals(notReplyWriterException.getMessage(), ReplyError.NOT_REPLY_WRITER.getDescription());
    }

    @Test
    void testRemove() {
        // given
        Reply reply = createReply(2L, "email", 2L);

        given(replyRepository.findById(anyLong()))
                .willReturn(Optional.of(reply));

        // when
        replyService.remove(reply.getId(), reply.getMember().getEmail());

        // then
        verify(replyRepository).delete(any());
    }

    @Test
    void testRemoveException() {
        Reply reply = createReply(2L, "email", 2L);

        given(replyRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when
        NotExistReplyException notExistReplyException =
                assertThrows(NotExistReplyException.class,
                        () -> replyService.remove(reply.getId(),
                                "email"));

        // then
        assertEquals(notExistReplyException.getMessage(),
                ReplyError.NOT_EXIST_REPLY.getDescription());
    }

    @Test
    @DisplayName("RemoveTest -> NotReplyWriterException")
    void testRemoveException2() {
        Reply reply = createReply(2L, "email", 2L);

        given(replyRepository.findById(anyLong()))
                .willReturn(Optional.of(reply));

        // when
        NotReplyWriterException notReplyWriterException =
                assertThrows(NotReplyWriterException.class,
                        () -> replyService.remove(reply.getId(), "email2"));

        // then
        assertEquals(notReplyWriterException.getMessage(),
                ReplyError.NOT_REPLY_WRITER.getDescription());
    }
}
package com.example.usedTrade.reply.service.impl;

import com.example.usedTrade.error.reply.exception.NotExistReplyException;
import com.example.usedTrade.error.reply.exception.NotReplyWriterException;
import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.reply.entity.Reply;
import com.example.usedTrade.reply.model.ReplyDto;
import com.example.usedTrade.reply.model.ReplyInput;
import com.example.usedTrade.reply.repository.ReplyRepository;
import com.example.usedTrade.reply.service.ReplyService;
import com.example.usedTrade.trade.entity.Trade;
import com.example.usedTrade.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final TradeRepository tradeRepository;

    @Override
    public void write(ReplyInput replyInput) {
        replyRepository.save(
                Reply.builder()
                .trade(Trade.builder().id(replyInput.getTrade_id()).build())
                .member(Member.builder().email(replyInput.getEmail()).build())
                .content(replyInput.getContent())
                .regDt(LocalDateTime.now())
                .build());
    }

    @Override
    @Transactional(readOnly = true)
    public ReplyDto get(long replyId) {
        Reply reply = findReply(replyId);

        return ReplyDto.entityToDto(reply);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyDto> getReplyList(long tradeId) {
        List<Reply> replyList = replyRepository.findByTrade_id(tradeId);

        return replyList.stream().map(ReplyDto::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyInput replyInput) {
        Reply reply = findReply(replyInput.getReply_id());

        validationWriter(reply.getMember(), replyInput.getEmail());

        reply.modifyReply(replyInput);

        replyRepository.save(reply);
    }

    @Override
    public void remove(long replyId, String email) {
        Reply reply = findReply(replyId);

        validationWriter(reply.getMember(), email);

        replyRepository.delete(reply);
    }

    private void validationWriter(Member member, String email) {
        if (!member.getEmail().equals(email)) {
            throw new NotReplyWriterException();
        }
    }

    private Reply findReply(long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(NotExistReplyException::new);
    }
}

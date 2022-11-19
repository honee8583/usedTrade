package com.example.usedTrade.reply.service;

import com.example.usedTrade.reply.model.ReplyDto;
import com.example.usedTrade.reply.model.ReplyInput;

import java.util.List;

public interface ReplyService {

    /**
     * 댓글 등록
     */
    void write(ReplyInput replyInput);

    /**
     * 댓글 조회
     */
    ReplyDto get(long replyId);

    /**
     * 댓글 목록 조회
     */
    List<ReplyDto> getReplyList(long tradeId);

    /**
     * 댓글 수정
     */
    void modify(ReplyInput replyInput);

    /**
     * 댓글 삭제
     */
    void remove(long replyId, String email);
}

package com.example.usedTrade.error.reply;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReplyError {
    NOT_EXIST_REPLY("존재하지 않는 댓글입니다."),
    NOT_REPLY_WRITER("댓글 작성자가 아닙니다.");

    private final String description;
}

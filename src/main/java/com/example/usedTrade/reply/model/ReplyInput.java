package com.example.usedTrade.reply.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReplyInput {
    private Long reply_id;
    private Long trade_id;
    private String email;
    private String content;
}

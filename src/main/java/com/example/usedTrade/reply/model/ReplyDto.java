package com.example.usedTrade.reply.model;

import java.time.LocalDateTime;

import com.example.usedTrade.reply.entity.Reply;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReplyDto {
    private Long id;
    private Long trade_id;
    private String email;
    private String content;

    private LocalDateTime regDt;
    private LocalDateTime upDt;

    public static ReplyDto entityToDto(Reply reply) {
        return ReplyDto.builder()
                .id(reply.getId())
                .trade_id(reply.getTrade().getId())
                .email(reply.getMember().getEmail())
                .content(reply.getContent())
                .regDt(reply.getRegDt())
                .upDt(reply.getUpDt())
                .build();
    }
}

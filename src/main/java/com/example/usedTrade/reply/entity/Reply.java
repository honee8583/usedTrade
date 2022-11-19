package com.example.usedTrade.reply.entity;

import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.reply.model.ReplyInput;
import com.example.usedTrade.trade.entity.Trade;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id")
    private Trade trade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_email")
    private Member member;

    private String content;

    private LocalDateTime regDt;

    private LocalDateTime upDt;

    public void modifyReply(ReplyInput replyInput) {
        this.content = replyInput.getContent();
        this.upDt = LocalDateTime.now();
    }
}

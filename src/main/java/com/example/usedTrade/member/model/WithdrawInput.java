package com.example.usedTrade.member.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class WithdrawInput {
    private String password;
    private boolean fromSocial;
}

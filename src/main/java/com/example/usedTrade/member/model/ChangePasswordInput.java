package com.example.usedTrade.member.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChangePasswordInput {
    private String originPassword;
    private String newPassword;
}

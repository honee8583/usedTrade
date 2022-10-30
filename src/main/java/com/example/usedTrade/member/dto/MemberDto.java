package com.example.usedTrade.member.dto;

import java.time.LocalDateTime;

import com.example.usedTrade.member.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberDto {
    private String email;
    private String password;
    private String name;
    private String address;
    private String address_detail;
    private String phone;
    private int trade_num;
    private String status;
    private boolean managerYn;
    private LocalDateTime regDt;

    // Email Auth
    private boolean emailAuthYn;
    private String emailAuthKey;
    private LocalDateTime emailAuthDt;

    public static MemberDto entityToDto(Member member) {
        return MemberDto.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .address(member.getAddress())
                .address_detail(member.getAddress_detail())
                .phone(member.getPhone())
                .trade_num(member.getTrade_num())
                .status(member.getStatus())
                .managerYn(member.isManagerYn())
                .regDt(member.getRegDt())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthKey(member.getEmailAuthKey())
                .emailAuthDt(member.getEmailAuthDt())
                .build();
    }
}

package com.example.usedTrade.member.dto;

import com.example.usedTrade.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
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
    private boolean fromSocial;
    private LocalDateTime regDt;

    public static MemberDto entityToDto(Member member) {
        return MemberDto.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .address(member.getAddress())
                .address_detail(member.getAddress_detail())
                .phone(member.getPhone())
                .trade_num(member.getTrade_num())
                .fromSocial(member.isFromSocial())
                .regDt(member.getRegDt())
                .build();
//                .status(member.getStatus())
//                .managerYn(member.isManagerYn())
//                .emailAuthYn(member.isEmailAuthYn())
//                .emailAuthKey(member.getEmailAuthKey())
//                .emailAuthDt(member.getEmailAuthDt())
    }
}

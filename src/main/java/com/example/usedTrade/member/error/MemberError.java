package com.example.usedTrade.member.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberError {
    NO_MEMBER_EXISTS("회원정보가 존재하지 않습니다"),
    MEMBER_ALREADY_EXISTS("이미 가입된 회원(이메일)입니다."),
    ALREADY_EMAIL_AUTHENTICATED("이메일 인증이 이미 처리되었습니다."),
    PASSWORD_NOT_MATCHED("비밀번호가 일치하지 않습니다."),
    RESET_PASSWORD_KEY_NOT_MATCHED("초기화 변경 키가 아닙니다."),
    RESET_PASSWORD_LIMIT_DT_NOT_AVAILABLE("초기화 가능 기간이 존재하지 않습니다."),
    RESET_PASSWORD_LIMIT_DT_PASSED("초기화 가능 기간이 지났습니다.");

    private final String description;
}

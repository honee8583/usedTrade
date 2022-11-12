package com.example.usedTrade.error.member.exception;

public class MemberEmailNotAuthenticatedException extends RuntimeException {
    public MemberEmailNotAuthenticatedException(String error) {
        super(error);
    }
}

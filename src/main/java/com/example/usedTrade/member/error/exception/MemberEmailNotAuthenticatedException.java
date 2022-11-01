package com.example.usedTrade.member.error.exception;

public class MemberEmailNotAuthenticatedException extends RuntimeException {
    public MemberEmailNotAuthenticatedException(String error) {
        super(error);
    }
}

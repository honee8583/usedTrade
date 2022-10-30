package com.example.usedTrade.exception;

public class MemberEmailNotAuthenticatedException extends RuntimeException {
    public MemberEmailNotAuthenticatedException(String error) {
        super(error);
    }
}

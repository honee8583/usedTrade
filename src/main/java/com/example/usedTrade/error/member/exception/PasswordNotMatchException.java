package com.example.usedTrade.error.member.exception;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String error) {
        super(error);
    }
}

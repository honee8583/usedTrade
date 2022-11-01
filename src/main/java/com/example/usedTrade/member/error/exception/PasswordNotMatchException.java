package com.example.usedTrade.member.error.exception;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String error) {
        super(error);
    }
}

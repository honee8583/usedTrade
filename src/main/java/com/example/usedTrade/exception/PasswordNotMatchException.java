package com.example.usedTrade.exception;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String error) {
        super(error);
    }
}

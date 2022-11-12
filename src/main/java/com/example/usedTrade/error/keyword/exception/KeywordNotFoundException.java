package com.example.usedTrade.error.keyword.exception;

import com.example.usedTrade.error.AbstractException;
import com.example.usedTrade.error.keyword.KeywordError;
import org.springframework.http.HttpStatus;

public class KeywordNotFoundException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return KeywordError.TRADE_NOT_EXISTS.getDescription();
    }
}

package com.example.usedTrade.error.trade.exception;

import com.example.usedTrade.error.AbstractException;
import com.example.usedTrade.error.trade.TradeError;
import org.springframework.http.HttpStatus;

public class NotInterestedException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return TradeError.NOT_INTERESTED_TRADE.getDescription();
    }
}

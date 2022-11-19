package com.example.usedTrade.error.trade.exception;

import com.example.usedTrade.error.AbstractException;
import com.example.usedTrade.error.trade.TradeError;
import org.springframework.http.HttpStatus;

public class AlreadyInterestedException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return TradeError.ALREADY_INTERESTED_TRADE.getDescription();
    }
}

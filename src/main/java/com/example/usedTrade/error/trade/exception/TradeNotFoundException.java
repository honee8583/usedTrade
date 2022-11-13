package com.example.usedTrade.error.trade.exception;

import com.example.usedTrade.error.AbstractException;
import com.example.usedTrade.error.trade.TradeError;
import org.springframework.http.HttpStatus;

public class TradeNotFoundException extends AbstractException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_GATEWAY.value();
    }

    @Override
    public String getMessage() {
        return TradeError.TRADE_NOT_FOUND.getDescription();
    }
}

package com.example.usedTrade.error.trade.exception;

import com.example.usedTrade.error.AbstractException;
import org.springframework.http.HttpStatus;

public class NotInterestedException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "좋아요한 게시글이 아닙니다.";
    }
}

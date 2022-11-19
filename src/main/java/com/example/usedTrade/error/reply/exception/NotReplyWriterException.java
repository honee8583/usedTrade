package com.example.usedTrade.error.reply.exception;

import com.example.usedTrade.error.AbstractException;
import com.example.usedTrade.error.reply.ReplyError;
import org.springframework.http.HttpStatus;

public class NotReplyWriterException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return ReplyError.NOT_REPLY_WRITER.getDescription();
    }
}

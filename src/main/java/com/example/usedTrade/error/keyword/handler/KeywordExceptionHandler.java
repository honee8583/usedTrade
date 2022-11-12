package com.example.usedTrade.error.keyword.handler;

import com.example.usedTrade.error.ErrorResponse;
import com.example.usedTrade.error.AbstractException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {"com.example.usedTrade.keyword.controller"})
public class KeywordExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    public ResponseEntity<ErrorResponse> keywordNotFoundExceptionHandler(AbstractException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                                            .statusCode(e.getStatusCode())
                                            .message(e.getMessage())
                                            .build();

        return new ResponseEntity<>(errorResponse,
                HttpStatus.resolve(e.getStatusCode()));
    }

}

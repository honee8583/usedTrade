package com.example.usedTrade.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class UsedTradeExceptionHandler {

    /**
     * Validation 예외 핸들러
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> ValidationErrorHandler(MethodArgumentNotValidException e) {

        List<String> errorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        log.error(errorList.toString());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(errorList.toString())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(HttpStatus.BAD_REQUEST.value()));
    }

    /**
     * 모든 컨트롤러 예외 처리
     */
    @ExceptionHandler(AbstractException.class)
    public ResponseEntity<ErrorResponse> tradeExceptionHandler(AbstractException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(e.getStatusCode())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(e.getStatusCode()));
    }
}

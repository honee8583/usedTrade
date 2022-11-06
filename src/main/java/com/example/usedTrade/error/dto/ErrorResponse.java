package com.example.usedTrade.error.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private String title;
    private Integer status;
    private LocalDateTime timestamp;
    private String developerMessage;
    private List<String> err;
}

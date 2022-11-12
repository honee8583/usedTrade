package com.example.usedTrade.error.member;

import lombok.Data;

@Data
public class ServiceResult {

    boolean result;
    MemberError errorCode;

    public ServiceResult() {
        this.result = true;
    }

    public ServiceResult(boolean result) {
        this.result = result;
    }

    public ServiceResult(boolean result, MemberError errorCode) {
        this.result = result;
        this.errorCode = errorCode;
    }
}

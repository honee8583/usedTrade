package com.example.usedTrade.member.entity;

public interface MemberStatus {
    /**
     * 가입 요청중인 회원
     */
    String MEMBER_STATUS_REQ = "REQ";

    /**
     * 사용가능한 회원
     */
    String MEMBER_STATUS_AVAILABLE = "AVAILABLE";

    /**
     * 정지된 회원
     */
    String MEMBER_STATUS_STOPPED = "STOPPED";

    /**
     * 탈퇴한 회원
     */
    String MEMBER_STATUS_WITHDRAW = "WITHDRAW";
}

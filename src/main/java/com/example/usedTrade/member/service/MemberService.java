package com.example.usedTrade.member.service;

import com.example.usedTrade.member.error.ServiceResult;
import com.example.usedTrade.member.dto.MemberDto;
import com.example.usedTrade.member.model.*;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService {

    /**
     * 회원가입
     */
    ServiceResult register(MemberInput memberInput);

    /**
     * uuid 일치여부로 이메일 인증
     */
    ServiceResult emailAuth(String uuid);

    /**
     * 해당 이메일의 회원정보 불러오기
     */
    MemberDto getInfo(String email);

    /**
     * 회원정보수정
     */
    ServiceResult update(ChangeMemberInput changeMemberInput);

    /**
     * 회원비밀번호 초기화(수정)
     */
    ServiceResult changePassword(String email, ChangePasswordInput passwordInput);

    /**
     * 비밀번호 초기화 메일 전송
     */
    ServiceResult sendResetPasswordEmail(ResetPasswordInput passwordInput);

    /**
     * 비밀번호 초기화키 확인
     */
    ServiceResult checkResetPasswordKey(String resetPasswordKey);

    /**
     * 비밀번호 초기화
     */
    ServiceResult resetPassword(ResetPasswordFormInput passwordInput);

    /**
     * 회원탈퇴
     */
    ServiceResult withdraw(String email, String password);

    /**
     * 소셜간편로그인, 일반로그인 여부
     */
    boolean getFromSocial(String email);
}

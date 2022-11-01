package com.example.usedTrade.member.repository;

import com.example.usedTrade.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    private Member createMember() {
        return Member.builder()
                .email("member_test")
                .password("member_test_password")
                .name("member_test_name")
                .address("member_test_address")
                .address_detail("member_test_detail")
                .phone("010-1234-5678")
                .trade_num(0)
                .regDt(LocalDateTime.now())
                .status(Member.MEMBER_STATUS_REQ)
                .managerYn(false)
                .emailAuthKey(UUID.randomUUID().toString())
                .emailAuthYn(false)
                .emailAuthDt(LocalDateTime.now().plusDays(1))
                .resetPasswordKey("")
                .resetPasswordLimitDt(LocalDateTime.now().plusDays(1))
                .build();
    }

    @Test
    void testSaveMember() {
        // given
        Member testMember = createMember();

        // when
        Member savedMember = memberRepository.save(testMember);

        // then
        assertEquals(testMember.getEmail(), savedMember.getEmail());
        assertEquals(testMember.getPassword(), savedMember.getPassword());
        assertEquals(testMember.getName(), savedMember.getName());
        assertEquals(testMember.getAddress(), savedMember.getAddress());
        assertEquals(testMember.getAddress_detail(), savedMember.getAddress_detail());
        assertEquals(testMember.getPhone(), savedMember.getPhone());
        assertEquals(testMember.getTrade_num(), savedMember.getTrade_num());
        assertEquals(testMember.getRegDt(), savedMember.getRegDt());
        assertEquals(testMember.getStatus(), savedMember.getStatus());
        assertEquals(testMember.isManagerYn(), savedMember.isManagerYn());
        assertEquals(testMember.getEmailAuthKey(), savedMember.getEmailAuthKey());
        assertEquals(testMember.getEmailAuthDt(), savedMember.getEmailAuthDt());
        assertEquals(testMember.isEmailAuthYn(), savedMember.isEmailAuthYn());
        assertEquals(testMember.getResetPasswordKey(), savedMember.getResetPasswordKey());
        assertEquals(testMember.getResetPasswordLimitDt(), savedMember.getResetPasswordLimitDt());
    }

}
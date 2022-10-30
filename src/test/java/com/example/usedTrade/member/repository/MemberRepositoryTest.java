package com.example.usedTrade.member.repository;

import com.example.usedTrade.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    private Member createMember() {
        String uuid = UUID.randomUUID().toString();
        LocalDateTime date = LocalDateTime.now();
        return Member.builder()
                .email("test@gmail.com")
                .password("password")
                .address("address")
                .address_detail("address_detail")
                .phone("010-1234-5678")
                .trade_num(1)
                .managerYn(false)
                .status(Member.MEMBER_STATUS_REQ)
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .emailAuthDt(null)
                .resetPasswordKey("")
                .resetPasswordLimitDt(null)
                .regDt(date)
                .build();
    }

    @Autowired
    MemberRepository memberRepository;

    @Test
    void saveMember() {
        // given
        Member member = createMember();
        LocalDateTime date = member.getRegDt();
        String uuid = member.getEmailAuthKey();

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertEquals("test@gmail.com", savedMember.getEmail());
        assertEquals("password", savedMember.getPassword());
        assertEquals("address", savedMember.getAddress());
        assertEquals("address_detail", savedMember.getAddress_detail());
        assertEquals("010-1234-5678", savedMember.getPhone());
        assertEquals(1, savedMember.getTrade_num());
        assertFalse(savedMember.isManagerYn());
        assertEquals(Member.MEMBER_STATUS_REQ, savedMember.getStatus());
        assertFalse(savedMember.isEmailAuthYn());
        assertEquals(uuid, savedMember.getEmailAuthKey());
        assertNull(savedMember.getEmailAuthDt());
        assertEquals("", savedMember.getResetPasswordKey());
        assertNull(savedMember.getResetPasswordLimitDt());
        assertEquals(date, savedMember.getRegDt());
    }

    @Test
    void testFindByEmailAuthKey() {
        // given
        Member member = createMember();
        String uuid = member.getEmailAuthKey();

        // when
        memberRepository.save(member);
        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);

        // then
        assertTrue(optionalMember.isPresent());
        assertEquals(uuid, optionalMember.get().getEmailAuthKey());
    }


}
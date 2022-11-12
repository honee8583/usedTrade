package com.example.usedTrade.member.service.impl;

import com.example.usedTrade.mail.MailComponents;
import com.example.usedTrade.member.dto.MemberDto;
import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.error.member.ServiceResult;
import com.example.usedTrade.member.model.ChangeMemberInput;
import com.example.usedTrade.member.model.MemberInput;
import com.example.usedTrade.member.model.ResetPasswordInput;
import com.example.usedTrade.member.repository.MemberRepository;
import com.example.usedTrade.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MailComponents mailComponents;

    @Autowired
    private MemberService memberService;

    private Member createMember() {
        return memberRepository.save(Member.builder()
                .email("test")
                .password("test")
                .name("test")
                .address("test")
                .address_detail("test")
                .phone("test")
                .trade_num(0)
                .regDt(LocalDateTime.now())
                .status(Member.MEMBER_STATUS_REQ)
                .fromSocial(false)
                .emailAuthKey(UUID.randomUUID().toString())
                .emailAuthYn(false)
                .emailAuthDt(LocalDateTime.now().plusDays(1))
                .resetPasswordKey(UUID.randomUUID().toString())
                .resetPasswordLimitDt(LocalDateTime.now().plusDays(1))
                .build());
    }

    @Test
    void testRegister() {
        // given
        MemberInput memberInput = MemberInput.builder()
                .email("test")
                .name("test")
                .address("test")
                .addressDetail("test")
                .phone("test")
                .password("test")
                .build();

        // when
        ServiceResult result = memberService.register(memberInput);

        // then
        assertTrue(result.isResult());
    }

    @Test
    void testEmailAuth() {
        // given
        Member savedMember = createMember();
        String emailAuthKey = savedMember.getEmailAuthKey();

        // when
        ServiceResult result = memberService.emailAuth(emailAuthKey);

        // then
        assertTrue(result.isResult());
    }

    @Test
    void testGetInfo() {
        // given
        memberService.register(MemberInput.builder()
                .email("test")
                .name("test")
                .address("test")
                .addressDetail("test")
                .phone("test")
                .password("test")
                .build());

        // when
        MemberDto savedMemberDto = memberService.getInfo("test");

        // then
        assertEquals("test", savedMemberDto.getEmail());
        assertEquals("test", savedMemberDto.getName());
        assertEquals("test", savedMemberDto.getAddress());
        assertEquals("test", savedMemberDto.getAddress_detail());
        assertEquals("test", savedMemberDto.getPhone());
    }

    @Test
    void testUpdate() {
        // given
        createMember();
        ChangeMemberInput input = ChangeMemberInput.builder()
                .email("test")
                .name("test2")
                .phone("test2")
                .address("test2")
                .addressDetail("test2")
                .build();

        // when
        ServiceResult result = memberService.update(input);

        // then
        assertTrue(result.isResult());
    }

    @Test
    void testSendResetPasswordEmail() {
        // given
        createMember();
        ResetPasswordInput input = ResetPasswordInput.builder()
                .email("test")
                .name("test")
                .build();

        // when
        ServiceResult result = memberService.sendResetPasswordEmail(input);

        // then
        assertTrue(result.isResult());
    }

    @Test
    void testCheckResetPasswordKey() {
        // given
        Member savedMember = createMember();
        ResetPasswordInput input = ResetPasswordInput.builder()
                .email("test")
                .name("test")
                .build();
        memberService.sendResetPasswordEmail(input);
        Optional<Member> optionalMember = memberRepository.findById("test");
        String resetPasswordKey = optionalMember.get().getResetPasswordKey();

        // when
        ServiceResult result = memberService.checkResetPasswordKey(resetPasswordKey);

        // then
        assertTrue(result.isResult());
    }

    @Test
    void testGetFromSocial() {
        // given
        createMember();
        String email = "test";

        // when
        boolean fromSocial = memberService.getFromSocial(email);

        // then
        assertTrue(!fromSocial);
    }
}
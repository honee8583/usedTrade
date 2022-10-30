package com.example.usedTrade.member.service;

import com.example.usedTrade.ServiceResult;
import com.example.usedTrade.member.dto.MemberDto;
import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.member.model.MemberInput;
import com.example.usedTrade.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void testRegister() {
        // given
        MemberInput memberInput = MemberInput.builder()
                .email("test@gmail.com")
                .password("password")
                .name("name")
                .address("address")
                .addressDetail("addressDetail")
                .phone("010-1234-5678")
                .build();

        // when
        ServiceResult result = memberService.register(memberInput);

        // then
        assertTrue(result.isResult());
    }

    @Test
    void testEmailAuth() {
        // given
        MemberInput memberInput = MemberInput.builder()
                .email("test@gmail.com")
                .password("password")
                .name("name")
                .address("address")
                .addressDetail("addressDetail")
                .phone("010-1234-5678")
                .build();

        memberService.register(memberInput);
        Optional<Member> optionalMember = memberRepository.findById(memberInput.getEmail());
        Member savedMember = optionalMember.get();
        String savedUuid = savedMember.getEmailAuthKey();

        // when
        ServiceResult emailAuthResult = memberService.emailAuth(savedUuid);

        // then
        System.out.println(emailAuthResult.getErrorCode().getDescription());
        System.out.println(savedMember);
        assertTrue(emailAuthResult.isResult());
    }

    @Test
    void testGetInfo() {
        // given
        String email = "test@test.com";
        MemberInput memberInput = MemberInput.builder()
                .email("test@test.com")
                .password("password")
                .name("name")
                .address("address")
                .addressDetail("addressDetail")
                .phone("010-1234-5678")
                .build();

        // when
        memberService.register(memberInput);
        MemberDto dto = memberService.getInfo(email);

        // then
        assertEquals("test@test.com", dto.getEmail());
        assertEquals("name", dto.getName());
        assertEquals("address", dto.getAddress());
        assertEquals("addressDetail", dto.getAddress_detail());
        assertEquals("010-1234-5678", dto.getPhone());
    }


}
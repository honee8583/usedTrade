package com.example.usedTrade.member.service.impl;

import com.example.usedTrade.member.entity.MemberRole;
import com.example.usedTrade.member.error.ServiceResult;
import com.example.usedTrade.UsedTradeApplication;
import com.example.usedTrade.member.error.MemberError;
import com.example.usedTrade.member.error.exception.MemberEmailNotAuthenticatedException;
import com.example.usedTrade.member.error.exception.MemberStopUserException;
import com.example.usedTrade.mail.MailComponents;
import com.example.usedTrade.member.dto.MemberDto;
import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.member.entity.MemberStatus;
import com.example.usedTrade.member.model.*;
import com.example.usedTrade.member.repository.MemberRepository;
import com.example.usedTrade.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;

    private static final Logger logger =
            LoggerFactory.getLogger(UsedTradeApplication.class);

    @Override
    public ServiceResult register(MemberInput memberInput) {
        // 존재하는 회원인지 체크
        Optional<Member> optionalMember = memberRepository.findById(memberInput.getEmail());
        if (optionalMember.isPresent()) {
            return new ServiceResult(false, MemberError.MEMBER_ALREADY_EXISTS);
        }

        String encPassword = BCrypt.hashpw(memberInput.getPassword(), BCrypt.gensalt());

        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .email(memberInput.getEmail())
                .password(encPassword)
                .name(memberInput.getName())
                .address(memberInput.getAddress())
                .address_detail(memberInput.getAddressDetail())
                .phone(memberInput.getPhone())
                .trade_num(0)
                .managerYn(false)
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .status(Member.MEMBER_STATUS_REQ)
                .regDt(LocalDateTime.now())
                .build();
        member.addMemberRole(MemberRole.USER);
        memberRepository.save(member);

        String email = memberInput.getEmail();
        String subject = "UsedTrade 사이트 가입을 축하드립니다.";
        String text = "<p>UsedTrade 사이트 가입을 축하드립니다.</p>" +
                "<p>아래 링크를 클릭하셔서 가입을 완료하세요</p>" +
                "<div><a href='http://localhost:8080/member/email-auth?id=" + uuid + "'>" +
                "회원가입" +
                "</a></div>";
        mailComponents.sendMail(email, subject, text);

        return new ServiceResult();
    }

    @Override
    public ServiceResult emailAuth(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, MemberError.NO_MEMBER_EXISTS);
        }

        Member member = optionalMember.get();

        // 찾은 계정이 이메일 인증이 되어 있는 경우
        if (member.isEmailAuthYn()) {
            return new ServiceResult(false, MemberError.ALREADY_EMAIL_AUTHENTICATED);
        }

        member.changeEmailAuth(MemberStatus.MEMBER_STATUS_AVAILABLE, true, LocalDateTime.now());
        logger.info(member.toString());
        memberRepository.save(member);

        return new ServiceResult();
    }

    @Override
    public MemberDto getInfo(String email) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원정보가 존재하지 않습니다.");
        }

        return optionalMember.map(MemberDto::entityToDto).orElse(null);
    }

    @Override
    public ServiceResult update(ChangeMemberInput changeMemberInput) {
        Optional<Member> optionalMember = memberRepository.findById(changeMemberInput.getEmail());
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, MemberError.NO_MEMBER_EXISTS);
        }

        Member member = optionalMember.get();
        member.changeMemberInfo(changeMemberInput.getName(), changeMemberInput.getAddress(),
                changeMemberInput.getAddressDetail(), changeMemberInput.getPhone());
        memberRepository.save(member);

        return new ServiceResult();
    }

    @Override
    public ServiceResult changePassword(String email, ChangePasswordInput passwordInput) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        if (!BCrypt.checkpw(passwordInput.getOriginPassword(), member.getPassword())) {
            return new ServiceResult(false, MemberError.PASSWORD_NOT_MATCHED);
        }

        String encNewPassword = BCrypt.hashpw(passwordInput.getNewPassword(), BCrypt.gensalt());
        member.changePassword(encNewPassword);
        memberRepository.save(member);

        return new ServiceResult();
    }

    @Override
    public ServiceResult sendResetPasswordEmail(ResetPasswordInput passwordInput) {
        // email, name
        Optional<Member> optionalMember =
                memberRepository.findByEmailAndName(
                        passwordInput.getEmail(),
                        passwordInput.getName());

        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, MemberError.NO_MEMBER_EXISTS);
        }

        Member member = optionalMember.get();

        String uuid = UUID.randomUUID().toString();

        member.changeResetPasswordKey(uuid, LocalDateTime.now().plusDays(1));   // 기간 하루로 설정
        memberRepository.save(member);

        String email = passwordInput.getEmail();
        String subject = "[usedTrade] 비밀번호 초기화 메일 입니다.";
        String text = "<p>usedTrade 비밀번호 초기화 메일입니다.</p>" +
                "<p>아래 링크를 클릭하셔서 비밀번호를 초기화 해주세요.</p>" +
                "<div>" +
                "<a href='http://localhost:8080/member/reset/passwordMail?resetPasswordKey=" + uuid + "'>" +
                "비밀번호 초기화 링크" +
                "</a></div>";
        mailComponents.sendMail(email, subject, text);

        return new ServiceResult();
    }

    @Override
    public ServiceResult checkResetPasswordKey(String resetPasswordKey) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(resetPasswordKey);
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, MemberError.NO_MEMBER_EXISTS);
        }

        Member member = optionalMember.get();

        if (!member.getResetPasswordKey().equals(resetPasswordKey)) {
            return new ServiceResult(false, MemberError.RESET_PASSWORD_KEY_NOT_MATCHED);
        }

        // 초기화 기간이 없는 경우
        if (member.getResetPasswordLimitDt() == null) {
            return new ServiceResult(false, MemberError.RESET_PASSWORD_LIMIT_DT_NOT_AVAILABLE);
        }

        // 초기화 가능시간이 지난경우
        if (member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            return new ServiceResult(false, MemberError.RESET_PASSWORD_LIMIT_DT_PASSED);
        }

        return new ServiceResult();
    }

    @Override
    public ServiceResult resetPassword(ResetPasswordFormInput passwordInput) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(passwordInput.getResetPasswordKey());
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, MemberError.NO_MEMBER_EXISTS);
        }

        Member member = optionalMember.get();
        String encPassword = BCrypt.hashpw(passwordInput.getNewPassword(), BCrypt.gensalt());

        // 초기화 기간이 없는 경우
        if (member.getResetPasswordLimitDt() == null) {
            return new ServiceResult(false, MemberError.RESET_PASSWORD_LIMIT_DT_NOT_AVAILABLE);
        }

        // 초기화 가능시간이 지난경우
        if (member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            return new ServiceResult(false, MemberError.RESET_PASSWORD_LIMIT_DT_PASSED);
        }

        member.resetPassword(encPassword);
        memberRepository.save(member);

        return new ServiceResult();
    }

    @Override
    public ServiceResult withdraw(String email, String password) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, MemberError.NO_MEMBER_EXISTS);
        }

        Member member = optionalMember.get();
        if (!member.isFromSocial()) {
            if (!BCrypt.checkpw(password, member.getPassword())) {
                return new ServiceResult(false, MemberError.PASSWORD_NOT_MATCHED);
            }
        }

        member.withdrawMember();
        memberRepository.save(member);

        return new ServiceResult();
    }

    @Override
    public boolean getFromSocial(String email) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        return optionalMember.get().isFromSocial();
    }
}

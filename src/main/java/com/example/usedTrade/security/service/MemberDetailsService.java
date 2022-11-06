package com.example.usedTrade.security.service;

import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.member.error.exception.MemberEmailNotAuthenticatedException;
import com.example.usedTrade.member.error.exception.MemberStopUserException;
import com.example.usedTrade.member.repository.MemberRepository;
import com.example.usedTrade.security.config.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));

        if (Member.MEMBER_STATUS_REQ.equals(member.getStatus())) {
            throw new MemberEmailNotAuthenticatedException("이메일 인증이 안된 계정입니다.");
        }

        if (Member.MEMBER_STATUS_STOPPED.equals(member.getStatus())) {
            throw new MemberStopUserException("정지된 회원입니다.");
        }

        if (Member.MEMBER_STATUS_WITHDRAW.equals(member.getStatus())) {
            throw new MemberStopUserException("탈퇴된 회원입니다.");
        }

        PrincipalDetails userDetails = new PrincipalDetails(
                member.getEmail(),
                member.getPassword(),
                member.getRoles().stream()
                        .map(role ->
                                new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toSet()));

        return userDetails;
    }
}

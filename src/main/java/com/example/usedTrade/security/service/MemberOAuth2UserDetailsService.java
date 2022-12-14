package com.example.usedTrade.security.service;

import com.example.usedTrade.UsedTradeApplication;
import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.member.entity.MemberRole;
import com.example.usedTrade.member.repository.MemberRepository;
import com.example.usedTrade.security.config.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberOAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("-----------------------------");
        log.info("userRequest: " + userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName: " + clientName);
        log.info(userRequest.getAdditionalParameters().toString());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("-------------------");
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info(k + ": " + v);
        });

        String email = null;
        if (clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }

        log.info("EMAIL: " + email);

        Member member = saveGoogleMember(email);    // register
        PrincipalDetails principalDetails = new PrincipalDetails(
                member.getEmail(),
                member.getPassword(),
                member.getRoles().stream()
                        .map(role ->
                                new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toSet()),
                oAuth2User.getAttributes());
//        httpSession.setAttribute("user", email);

        return principalDetails;
    }

    private Member saveGoogleMember(String email) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            return optionalMember.get();
        }

        Member member = Member.builder()
                .email(email)
                .password(BCrypt.hashpw("1111", BCrypt.gensalt()))
                .name("Social Member")
                .address("Social Member")
                .address_detail("Social Member")
                .phone("Social Member")
                .trade_num(0)
                .status(Member.MEMBER_STATUS_AVAILABLE)
                .fromSocial(true)
                .regDt(LocalDateTime.now())
                .build();
        member.addMemberRole(MemberRole.USER);

        Member savedMember = memberRepository.save(member);

        return savedMember;
    }
}

package com.example.usedTrade.security.config;

import com.example.usedTrade.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.*;

@Getter
@Setter
@ToString
public class PrincipalDetails extends User implements OAuth2User{

    private String email;
    private String password;
    private String name;
    private Map<String, Object> attr;

    // Normal Login
    public PrincipalDetails(String username, String password,
                            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
    }

    // OAuth2Login
    public PrincipalDetails(String username, String password,
                            Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attr) {
        super(username, password, authorities);
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}

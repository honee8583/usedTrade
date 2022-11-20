package com.example.usedTrade.security.config;

import com.example.usedTrade.security.service.MemberOAuth2UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberOAuth2UserDetailsService oAuth2UserDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/", "/member/**").permitAll()
                .antMatchers("/trade/**", "/api/interest/**").authenticated();

        http.formLogin()
                .loginPage("/member/login")
                .failureHandler(getFailureHandler())
                .defaultSuccessUrl("/")
                .permitAll();

        http.oauth2Login().defaultSuccessUrl("/");

        http.logout()
                .logoutRequestMatcher(
                        new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);   // 세션 초기화

        http.exceptionHandling()
                .accessDeniedPage("/error/denied");

        super.configure(http);
    }

}

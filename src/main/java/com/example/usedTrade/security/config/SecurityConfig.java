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

        // jwt
//        http.httpBasic().disable()  // 세션 방식 사용x
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                    .addFilter(jwtAuthorizationFilter)   //formLogin().disable()때문에 동작하지 않는
//                    .addFilter(jwtAuthenticationFilter);

        http.authorizeRequests()
                .antMatchers("/", "/member/**").permitAll()
                .antMatchers("/trade/**", "/api/trade/**").authenticated();

        http.formLogin()
                .loginPage("/member/login")
                .failureHandler(getFailureHandler())   // 로그인 실패 케이스
                .defaultSuccessUrl("/")
                .permitAll();

        http.oauth2Login().defaultSuccessUrl("/");

        http.logout()
                .logoutRequestMatcher(
                        new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")  // 로그아웃 성공후 이동 페이지
                .invalidateHttpSession(true);   // 세션 초기화

        super.configure(http);
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(memberService)
//                .passwordEncoder(getPasswordEncoder());
//
//        super.configure(auth);
//    }
}

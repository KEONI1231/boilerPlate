package com.mooko.dev.security.config;

import com.mooko.dev.contrant.Constants;
import com.mooko.dev.security.filter.JwtAuthenticationFilter;
import com.mooko.dev.security.filter.JwtExceptionFilter;
import com.mooko.dev.security.handler.jwt.JwtAccessDeniedHandler;
import com.mooko.dev.security.handler.jwt.JwtAuthEntryPoint;
import com.mooko.dev.security.handler.signin.DefaultFailureHandler;
import com.mooko.dev.security.handler.signin.DefaultSuccessHandler;
import com.mooko.dev.security.handler.signin.OAuth2SuccessHandler;
import com.mooko.dev.security.handler.singout.CustomSignOutResultHandler;
import com.mooko.dev.security.service.CustomUserDetailService;
import com.mooko.dev.security.service.CustomOAuth2UserService;
import com.mooko.dev.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import com.mooko.dev.security.filter.GlobalLoggerFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomSignOutResultHandler customSignOutProcessHandler;
    private final CustomSignOutResultHandler customSignOutResultHandler;
    private final DefaultSuccessHandler defaultSuccessHandler;
    private final DefaultFailureHandler defaultFailureHandler;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    private final CustomUserDetailService customUserDetailService;
    private final JwtUtil jwtUtil;

    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) -> //세션 생성 STATELESS로 설정. 세션을 사용하지 않고 상태를 유지하지 않는 방식으로 인증.
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(registry -> //특정 url 패턴에 대한 접근 권한. 인증 없이 접근 허용.
                        registry
                                .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll()
                                .anyRequest().authenticated()
                )
//                .logout(configurer ->
//                        configurer
//                                .logoutUrl("/auth/sign-out")
//                                .addLogoutHandler(customSignOutProcessHandler)
//                                .logoutSuccessHandler(customSignOutResultHandler)
//                )

                .exceptionHandling(configurer -> // 인증 실패 시 jwtAuthEntryPoint로 처리하고 접근 거부 시 jwtAccessDeniedHandler
                        configurer
                                .authenticationEntryPoint(jwtAuthEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .oauth2Login(config -> config
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(defaultFailureHandler)
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)
                        )
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil, customUserDetailService),
                        LogoutFilter.class)
                .addFilterBefore(
                        new JwtExceptionFilter(),
                        JwtAuthenticationFilter.class)
                .addFilterBefore(
                        new GlobalLoggerFilter(),
                        JwtExceptionFilter.class)

                .getOrBuild();
    }
}

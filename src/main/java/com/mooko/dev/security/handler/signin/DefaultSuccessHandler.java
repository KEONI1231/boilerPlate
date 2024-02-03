package com.mooko.dev.security.handler.signin;

import com.mooko.dev.dto.response.JwtTokenDto;
import com.mooko.dev.repository.UserRepository;
import com.mooko.dev.security.info.UserPrincipal;
import com.mooko.dev.utility.CookieUtil;
import com.mooko.dev.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class DefaultSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    //로그인이 안되어있는 시점에서 처음 로그인 할때.
    //
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        JwtTokenDto tokenDto = jwtUtil.generateTokens(userPrincipal.getId(),
                userPrincipal.getRole());

        userRepository.updateRefreshTokenAndLoginStatus(userPrincipal.getId(),
                tokenDto.refreshToken(),
                true);


        setSuccessWebResponse(response, tokenDto);
    }

    private void setSuccessWebResponse(HttpServletResponse response, JwtTokenDto tokenDto)
            throws IOException {

        CookieUtil.addCookie(response, "access_token", tokenDto.accessToken());

        CookieUtil.addSecureCookie(response,
                "refresh_token", tokenDto.refreshToken(),
                jwtUtil.getRefreshTokenExpirePeriod());

        response.sendRedirect("http://localhost:3000");
    }
}

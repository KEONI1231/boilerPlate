package com.mooko.dev.security.filter;

import com.mooko.dev.contrant.Constants;
import com.mooko.dev.security.info.UserPrincipal;
import com.mooko.dev.security.service.CustomUserDetailService;
import com.mooko.dev.utility.HeaderUtil;
import com.mooko.dev.utility.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor //필터는 서블릿이 제공, 요청이 들어오면 얘가 가로챔.
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 접두사를 Request Header에서 접두사를 제거한 토큰 추출.
        String token = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new IllegalArgumentException("Authorization Header Is Not Found!"));


        //토큰 유효성 검증, 토큰에 담긴 정보 조각(클레임 추출)
        Claims claims = jwtUtil.validateToken(token);

        //UserPrincipal : 인증된 사용자 정보
        UserPrincipal userPrincipal = (UserPrincipal) customUserDetailService.loadUserById(
                claims.get(Constants.USER_ID_CLAIM_NAME, Long.class));

        //credential이 null? 그냥 null로 하더라.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities());

        //생성된 인증 토큰에 요청과 관련된 상세 정보를 추가.
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //보안 컨텍스트 설정.
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);

        //현재 필터의 처리가 완료된 후 요청이 필터 체인을 따라 다음 필터로 이동 또는 컨트롤러에 도달하도록 함.
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Constants.NO_NEED_AUTH_URLS.contains(request.getRequestURI());
    }
}

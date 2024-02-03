package com.mooko.dev.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class GlobalLoggerFilter extends OncePerRequestFilter {
    //여긴 그냥 로그찍는 필터인듯.
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("[Global] HTTP Request Received! ({} {} {})",
                request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURI());

        request.setAttribute("INTERCEPTOR_PRE_HANDLE_TIME",  System.currentTimeMillis());

        //filterChain.doFilter 를 통해서 다음 필터를 호출해줘야함.
        //있으면 다음 filter 호출, 없으면 servlet이 호출.
        filterChain.doFilter(request, response);

        Long preHandleTime = (Long) request.getAttribute("INTERCEPTOR_PRE_HANDLE_TIME");
        Long postHandleTime = System.currentTimeMillis();

        log.info("[Global] HTTP Request Has Been Processed! It Tokes {}ms. ({} {} {})",
                postHandleTime - preHandleTime,
                request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURI());
    }
}

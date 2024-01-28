package com.mooko.dev.intercepter.pre;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserIdInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        final Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        //현재 인등된 사용자의 정보를 가져오는 것.
        /**
         * SecurityContextHolder
         *  -> 현재 실행중인 스레드에 대한 보안 컨텍스트에 접근할 수 있는 방법을 제공.
         * getContext
         *  -> 현재 스레드의 SecurityContext 인스턴스를 반환.
         * getAunthentication()
         *  -> 현재 인증된 사용자의 Authentication 객체를 반환.
         */

        request.setAttribute("USER_ID", authentication.getName());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}

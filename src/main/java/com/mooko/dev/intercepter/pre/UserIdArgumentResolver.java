package com.mooko.dev.intercepter.pre;


import com.mooko.dev.annotation.UserId;
import com.mooko.dev.dto.type.ErrorCode;
import com.mooko.dev.exception.CommonException;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Long.class)
                && parameter.hasParameterAnnotation(UserId.class);
    }


    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        final Object userIdObj = webRequest.getAttribute("USER_ID", WebRequest.SCOPE_REQUEST);

        if (userIdObj == null) {
            throw new CommonException(ErrorCode.INVALID_HEADER_ERROR);
        }

        return Long.valueOf(userIdObj.toString());
    }
}
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
                && parameter.hasParameterAnnotation(UserId.class); //true or false

        //UserId 어노테이션이 붙어있고 타입이 Long.class인 파라미터를 해석하여
        //요청의 특정 속성(USER_ID)에서 값을 가져와 해당 파라미터의 인자로 제공.
    }


    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        final Object userIdObj
                = webRequest.getAttribute("USER_ID", WebRequest.SCOPE_REQUEST);
        if (userIdObj == null) {
            throw new CommonException(ErrorCode.INVALID_HEADER_ERROR);
        }
        return Long.valueOf(userIdObj.toString());
    }
}

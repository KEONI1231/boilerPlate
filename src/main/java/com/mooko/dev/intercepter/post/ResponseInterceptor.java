package com.mooko.dev.intercepter.post;

import com.mooko.dev.dto.common.ResponseDto;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.mooko.dev")
public class ResponseInterceptor implements ResponseBodyAdvice<Object> {
    //응답 본문 처리 : 이 클래스는 응답이 클라이언트로 반환되기 전에 응답 본문('body')을
    //가로채어 필요한 사전 처리를 할 수 있습니다.
    //만약 응답 본문이 ResponseDto 타입이라면, 이 타입 내에 정의된 HTTP 상태코드(httpStatus)를
    //응답의 상태 코드로 설정.

    @Override
    public boolean supports(@NonNull MethodParameter returnType,
                            @NonNull Class converterType) {
        return true;
    }
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {

        if (returnType.getParameterType() == ResponseDto.class) {
            HttpStatus status = ((ResponseDto<?>) body).httpStatus();
            response.setStatusCode(status);
        }

        return body;
    }
}

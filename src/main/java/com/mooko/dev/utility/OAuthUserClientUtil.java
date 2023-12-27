package com.mooko.dev.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mooko.dev.dto.type.ESocialType;
import com.mooko.dev.dto.type.ErrorCode;
import com.mooko.dev.exception.CommonException;
import com.mooko.dev.security.info.response.GoogleUserResponse;
import com.mooko.dev.security.info.response.KakaoUserResponse;
import com.mooko.dev.security.info.response.NaverUserResponse;
import com.mooko.dev.security.info.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;


@Slf4j
@Component
public class OAuthUserClientUtil {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserResponse getResponse(ESocialType socialType, Map<String, Object> attributes) {
        Class<? extends UserResponse> responseClass = determineResponseClass(socialType);
        return objectMapper.registerModule(new JavaTimeModule()).convertValue(attributes, responseClass);
    }

    private Class<? extends UserResponse> determineResponseClass(ESocialType socialType) {
        return switch (socialType) {
            case KAKAO -> KakaoUserResponse.class;
            case NAVER -> NaverUserResponse.class;
            case GOOGLE -> GoogleUserResponse.class;
            default -> throw new CommonException(ErrorCode.NOT_FOUND_SOCIAL_TYPE);
        };
    }
}
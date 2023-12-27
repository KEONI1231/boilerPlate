package com.mooko.dev.security.info.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mooko.dev.dto.type.ESocialType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleUserResponse implements UserResponse{

    private String id;
    private String email;
    private String verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;

    @Builder
    public GoogleUserResponse(String id, String email, String verified_email, String name, String given_name, String family_name, String picture, String locale) {
        this.id = id;
        this.email = email;
        this.verified_email = verified_email;
        this.name = name;
        this.given_name = given_name;
        this.family_name = family_name;
        this.picture = picture;
        this.locale = locale;
    }

    @Override
    public ESocialType supportServer(){
        return ESocialType.GOOGLE;
    }

    @Override
    public String getId(){
        return id;
    }

    @Override
    public Class<? extends UserResponse> getImplementationClass() {
        return GoogleUserResponse.class;
    }
}

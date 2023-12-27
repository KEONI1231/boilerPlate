package com.mooko.dev.security.info.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mooko.dev.dto.type.ESocialType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoUserResponse implements UserResponse {

    private Long id;
    private boolean hasSignedUp;
    private LocalDateTime connectedAt;
    private KakaoAccount kakaoAccount;
    @JsonProperty("properties")
    private Map<String, Object> properties;
    private Profile profile;

    @Builder
    public KakaoUserResponse(Long id, boolean hasSignedUp, LocalDateTime connectedAt, KakaoAccount kakaoAccount, Map<String, Object> properties, Profile profile) {
        this.id = id;
        this.hasSignedUp = hasSignedUp;
        this.connectedAt = connectedAt;
        this.kakaoAccount = kakaoAccount;
        this.properties = properties;
        this.profile = profile;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class KakaoAccount {
        private boolean profileNeedsAgreement;
        private boolean profileNicknameNeedsAgreement;
        private boolean profileImageNeedsAgreement;
        private Profile profile;
        private boolean nameNeedsAgreement;
        private String name;
        private boolean emailNeedsAgreement;
        private boolean isEmailValid;
        private boolean isEmailVerified;
        private String email;
        private boolean ageRangeNeedsAgreement;
        private String ageRange;
        private boolean birthyearNeedsAgreement;
        private String birthyear;
        private boolean birthdayNeedsAgreement;
        private String birthday;
        private String birthdayType;
        private boolean genderNeedsAgreement;
        private String gender;
        private boolean phoneNumberNeedsAgreement;
        private String phoneNumber;
        private boolean ciNeedsAgreement;
        private String ci;
        private LocalDateTime ciAuthenticatedAt;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Profile {
        private String nickname;
        private String thumbnailImageUrl;
        private String profileImageUrl;
        private boolean isDefaultImage;

    }

    @Override
    public ESocialType supportServer(){
        return ESocialType.KAKAO;
    }

    @Override
    public String getId(){
        return String.valueOf(id);
    }

    @Override
    public Class<? extends UserResponse> getImplementationClass() {
        return KakaoUserResponse.class;
    }
}
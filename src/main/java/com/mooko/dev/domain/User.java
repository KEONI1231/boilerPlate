package com.mooko.dev.domain;

import com.mooko.dev.dto.type.ERole;
import com.mooko.dev.dto.type.ESocialType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "serial_id", nullable = false, unique = true)
    private String serialId; //socialId

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    private ERole role;  //회원가입시 기본값은 GUEST, 추가가입 후에는 USER

    @Enumerated(EnumType.STRING)
    private ESocialType socialType;

    @Column(name = "is_login", columnDefinition = "TINYINT(1)")
    private boolean isLogin;        //0은 false, 1은 true

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    public User(String serialId, String nickname, String profileImageUrl, LocalDate createdDate, ERole role, ESocialType socialType, boolean isLogin, String refreshToken) {
        this.serialId = serialId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.createdDate = createdDate;
        this.role = role;
        this.socialType = socialType;
        this.isLogin = isLogin;
        this.refreshToken = refreshToken;
    }

    public static User createUser(String serialId, ESocialType socialType) {
        return User.builder()
                .serialId(serialId)
                .createdDate(LocalDate.now())
                .role(ERole.GUEST)
                .socialType(socialType)
                .isLogin(true)
                .build();
    }

    public void updateLoginStatus(boolean isLogin) {
        this.isLogin = isLogin;
    }
}

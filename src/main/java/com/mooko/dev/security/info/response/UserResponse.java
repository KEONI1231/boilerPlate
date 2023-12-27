package com.mooko.dev.security.info.response;

import com.mooko.dev.dto.type.ERole;
import com.mooko.dev.dto.type.ESocialType;

public interface UserResponse {

    ESocialType supportServer();

    String getId();

    Class<? extends UserResponse> getImplementationClass();
}

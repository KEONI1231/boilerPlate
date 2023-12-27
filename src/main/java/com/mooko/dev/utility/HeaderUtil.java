package com.mooko.dev.utility;

import com.mooko.dev.dto.type.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import com.mooko.dev.exception.CommonException;
import org.springframework.util.StringUtils;

import java.util.Optional;


public class HeaderUtil {
    public static Optional<String> refineHeader(HttpServletRequest request, String header, String prefix) {
        String unpreparedToken = request.getHeader(header);

        if (!StringUtils.hasText(unpreparedToken) || !unpreparedToken.startsWith(prefix)) {
            throw new CommonException(ErrorCode.INVALID_HEADER_ERROR);
        }

        return Optional.of(unpreparedToken.substring(prefix.length()));
    }
}

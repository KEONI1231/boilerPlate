package com.mooko.dev.service;

import com.mooko.dev.domain.User;
import com.mooko.dev.repository.UserRepository;
import com.mooko.dev.security.info.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

}

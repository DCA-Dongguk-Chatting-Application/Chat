package com.dongguk.chat.domain.user.service;

import com.dongguk.chat.domain.user.User;
import com.dongguk.chat.domain.user.dto.UserCreateReq;
import com.dongguk.chat.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User userCreate(UserCreateReq userCreateReq){
        String encodedPassword = passwordEncoder.encode(userCreateReq.getPassword());
        User reqUser = User.register(userCreateReq, encodedPassword);

        User createUser = userRepository.save(reqUser);
        return createUser;
    }
}

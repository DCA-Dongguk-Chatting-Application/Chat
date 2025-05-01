package com.dongguk.chat.util;

import com.dongguk.chat.domain.user.User;
import com.dongguk.chat.domain.user.dto.UserCreateReq;
import com.dongguk.chat.domain.user.dto.UserResponseDto;
import com.dongguk.chat.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserResponseDto userCreate(UserCreateReq userCreateReq){
        String encodedPassword = passwordEncoder.encode(userCreateReq.getPassword());
        User reqUser = User.register(userCreateReq, encodedPassword);

        System.out.println("reqUserId :    " + reqUser.getUsername());
        User savedUser = userRepository.save(reqUser);
        return UserResponseDto.fromUser(savedUser);
    }

    public User getUserInfo(Long userId){
        return userRepository.findById(userId).get();
    }
}

package com.dongguk.chat.security.service;

import com.dongguk.chat.domain.user.User;
import com.dongguk.chat.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> findUser = userRepository.findUsersByUsername(username);
        if(findUser.isEmpty()){
            throw new UsernameNotFoundException("user can not found!");
        }
        return new UserDetailsImpl(findUser.get());
    }
}

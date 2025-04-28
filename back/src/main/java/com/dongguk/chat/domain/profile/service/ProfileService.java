package com.dongguk.chat.domain.profile.service;

import com.dongguk.chat.domain.profile.UserProfile;
import com.dongguk.chat.domain.profile.dto.ProfileReqDto;
import com.dongguk.chat.domain.profile.dto.ProfileResponse;
import com.dongguk.chat.domain.profile.repository.ProfileRepository;
import com.dongguk.chat.domain.user.User;
import com.dongguk.chat.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileResponse createUserProfile(ProfileReqDto profileReqDto){
        User findUser = userRepository.findById(profileReqDto.getUserId()).get();

        UserProfile reqUserProfile = UserProfile.create(profileReqDto, findUser);
        UserProfile savedProfile = profileRepository.save(reqUserProfile);

        userRepository.save(findUser);

        return ProfileResponse.from(savedProfile);

    }
}

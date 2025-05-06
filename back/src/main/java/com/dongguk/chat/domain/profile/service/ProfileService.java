package com.dongguk.chat.domain.profile.service;

import com.dongguk.chat.domain.profile.UserProfile;
import com.dongguk.chat.domain.profile.dto.ProfileReqDto;
import com.dongguk.chat.domain.profile.dto.ProfileResponse;
import com.dongguk.chat.domain.profile.repository.ProfileRepository;
import com.dongguk.chat.domain.user.User;
import com.dongguk.chat.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final FileStorageProperties fileStorageProperties;

    public ProfileResponse createUserProfile(ProfileReqDto profileReqDto) throws IOException {
        System.out.println("id: -------------" + profileReqDto.getUserId());
        User findUser = userRepository.findById(profileReqDto.getUserId()).get();

        String imagePath = saveProfileImage(profileReqDto.getImage());


        UserProfile reqUserProfile = UserProfile.create(profileReqDto, findUser, imagePath);
        UserProfile savedProfile = profileRepository.save(reqUserProfile);

        userRepository.save(findUser);

        return ProfileResponse.from(savedProfile);
    }



    private String saveProfileImage(MultipartFile file) throws IOException{
        try{
            if(file == null || file.isEmpty()){
                return null;
            }

            String ext = getExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID() + ext;

            Path uploadPath = Paths.get(fileStorageProperties.getUploadDir());
            Files.createDirectories(uploadPath);

            Path savePath = uploadPath.resolve(fileName);
            file.transferTo(savePath);

            return "/static/profile/" +fileName;
        }catch (IOException ex){
            ex.printStackTrace();;
            throw ex;
        }
    }

    //파일의 확장자를 추출
    private String getExtension(String originalFilename){
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    public ProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        UserProfile profile = user.getProfile();

        return ProfileResponse.from(profile);
    }

}

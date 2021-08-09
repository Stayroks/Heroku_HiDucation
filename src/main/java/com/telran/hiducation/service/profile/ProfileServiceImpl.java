package com.telran.hiducation.service.profile;

import com.telran.hiducation.dao.products.ApplicationRepository;
import com.telran.hiducation.dao.users.UserRepository;
import com.telran.hiducation.pojo.dto.ProductInfoDto;
import com.telran.hiducation.pojo.dto.ResponseSuccessDto;
import com.telran.hiducation.pojo.dto.UserProfileDto;
import com.telran.hiducation.pojo.entity.ApplicationEntity;
import com.telran.hiducation.pojo.entity.UserEntity;
import com.telran.hiducation.service.ProcessingUserData;
import com.telran.hiducation.service.apps.ApplicationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationService applicationService;
    private final ProcessingUserData userData;
    private final ModelMapper mapper;

    @Override
    public UserProfileDto getProfile(String userEmail) {
        // Get user entity
        UserEntity user = userData.getUserByEmail(userEmail);
        return mapper.map(user, UserProfileDto.class);
    }

    @Override
    public ResponseSuccessDto updateProfile(Principal principal, UserProfileDto userDto) {
        if (principal == null) {
            // Screaming it's all gone
            throw new UsernameNotFoundException("You are logged in as an unauthorized user");
        }
        if (!principal.getName().equals(userDto.getEmail())) {
            // Shouting everything is gone,
            // and then we solve this problem
            // TODO: 01/08/2021
        }
        // Get user entity
        UserEntity userEntity = userData.getUserByPrincipal(principal);

        // We fill in the model for saving to the database
        UserEntity entity = mapperToUserEntity(userEntity, userDto);

        // Saving the model to the repository
        userRepository.save(entity);

        // Collecting a collection of installed applications
        // Adding applications to the user
        saveAppToCollection(principal, userEntity, userDto);

        return ResponseSuccessDto.builder()
                .message("User profile updated successfully")
                .build();
    }

    private UserEntity mapperToUserEntity(UserEntity user, UserProfileDto userDto) {
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setPassword(user.getPassword());
        userEntity.setAccountConfirmed(user.isAccountConfirmed());
        userEntity.setRoles(user.getRoles());
        userEntity.setApplications(user.getApplications());
        return userEntity;
    }

    private void saveAppToCollection(Principal principal, UserEntity userEntity, UserProfileDto userDto) {
        Collection<ApplicationEntity> applications = userEntity.getApplications();
        // **************************************
        if (applications.size() == 0) {
            System.out.println("Privet");
        }
        // **************************************
        for (ProductInfoDto application : userDto.getApplications()) {
            ApplicationEntity app = applicationRepository.findByAppName(application.getAppName());

            if (app == null) {
                continue;
            }
            if (!applications.contains(app)) {
                applicationService.addApplicationToUserByApplicationName(principal, application.getAppName());
            }
        }

    }

}

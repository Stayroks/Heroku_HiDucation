package com.telran.hiducation.service.users;

import com.telran.hiducation.dao.users.RoleRepository;
import com.telran.hiducation.dao.users.UserRepository;
import com.telran.hiducation.emails.SendEmail;
import com.telran.hiducation.exceptions.UsernameDuplicateException;
import com.telran.hiducation.pojo.dto.PasswordResetDto;
import com.telran.hiducation.pojo.dto.ResponseSuccessDto;
import com.telran.hiducation.pojo.dto.UserCredentialsDto;
import com.telran.hiducation.pojo.entity.RoleEntity;
import com.telran.hiducation.pojo.entity.UserEntity;
import com.telran.hiducation.service.ProcessingUserData;
import com.telran.hiducation.utills.ProcessHashcode;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.telran.hiducation.enums.Roles.ROLE_USER;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProcessingUserData userData;
    private final Environment environment;
    private final SendEmail sendEmail;
    private final ProcessHashcode processHashcode;


    @Override
    public ResponseSuccessDto createNewUser(UserCredentialsDto credentials) {

        // Get the object stored in the database by the specified parameter
        Optional<UserEntity> user = userRepository.findById(credentials.getEmail());
        // Check contains object data
        if (user.isPresent()) {
            // Check email confirmation
            if (user.get().isAccountConfirmed()) {
                // If the email is confirmed then throw the "User already exists" exception
                throw new UsernameDuplicateException(String.format("User with email: %s already exist", credentials.getEmail()));
            }
        }
        // By default, we assign all users the USER role
        // Get the USER role from the repository
        RoleEntity role = roleRepository.findByName(ROLE_USER.name());
        ArrayList<RoleEntity> roleList = new ArrayList<>();

        // If the role has not been added yet, add to the repository
        if (role == null) {
            roleRepository.save(RoleEntity.builder().name(ROLE_USER.name()).build());
            // We take out the entity and assign the value
            role = roleRepository.findByName(ROLE_USER.name());
        }
        roleList.add(role);
        // If the user is not in the database
        if (!user.isPresent()) {
            // We fill in the model for saving to the database
            UserEntity userEntity = UserEntity.builder()
                .email(credentials.getEmail())
                .password(passwordEncoder.encode(credentials.getPassword()))
                .roles(roleList)
                .accountConfirmed(false)
                .build();

            // Saving the model to the repository
            userRepository.save(userEntity);
        }
        // encode email
        String hash = processHashcode.encodeStr(credentials.getEmail());
        // collecting a link to confirm registration
        String urlToMessage = String.format("%s/%s", createUrlRegistration(), hash);
        // Call the method of sending a letter to the user from the SendEmail class
        return ResponseSuccessDto.builder()
                .message(sendEmail.confirmEmail(credentials.getEmail(), urlToMessage))
                .build();
    }


    @Override
    public ResponseSuccessDto confirmRegistration(String hash) {
        // Decode the hash
        String email = processHashcode.decodeHash(hash);
        // Get the user stored in the database by the email
        UserEntity user = userData.getUserByEmail(email);
        // Set to TRUE confirmation email registration
        user.setAccountConfirmed(true);
        // Refresh user data
        userRepository.save(user);
        return ResponseSuccessDto.builder()
                .message("redirect:/user/login").build();
    }

    @Override
    public ResponseSuccessDto replacePassword(Principal principal, PasswordResetDto passwordResetDto) {
        // Get the object stored in the database by the email
        UserEntity userEntity = userData.getUserByPrincipal(principal);
        // Get the old password provided by the user
        String rawPassword = passwordResetDto.getOldPassword();
        // Get the old password from database
        String encodePassword = userEntity.getPassword();
        // If the old passwords do not match, throw an exception
        if (!passwordEncoder.matches(rawPassword, encodePassword)) {
            throw new RuntimeException("The current password does not match");
        }
        // Change passwords
        userEntity.setPassword(passwordEncoder.encode(passwordResetDto.getNewPassword()));
        // Refresh user data
        userRepository.save(userEntity);
        return ResponseSuccessDto.builder().message("Password changed successfully").build();
    }

    private String getAppHost() {
//        return environment.getProperty("app.host.url") + environment.getProperty("server.port");
        return environment.getProperty("app.host.url");
    }

    private String createUrlRegistration() {
        return String.format("%s/%s/%s",
                getAppHost(),
                environment.getProperty("endpoint.url.user.controller"),
                environment.getProperty("endpoint.url.user.registration")
        );
    }

}
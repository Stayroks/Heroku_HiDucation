package com.telran.hiducation.service;

import com.telran.hiducation.dao.users.UserRepository;
import com.telran.hiducation.pojo.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProcessingUserData {

    private final UserRepository userRepository;

    public UserEntity getUserByPrincipal(Principal principal) {
        // Get the email of the user
        String userName = principal.getName();
        return getUserByEmail(userName);
    }

    public UserEntity getUserByEmail(String email) {
        if (email == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // Get the object stored in the database by email
        Optional<UserEntity> userEntity = userRepository.findById(email);
        // Check contains object data
        if (!userEntity.isPresent()) {
            throw new UsernameNotFoundException(String.format("User with email: %s not found", email));
        }
        return userEntity.get();
    }

}

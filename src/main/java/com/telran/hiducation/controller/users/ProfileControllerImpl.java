package com.telran.hiducation.controller.users;

import com.telran.hiducation.pojo.dto.UserProfileDto;
import com.telran.hiducation.service.profile.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@AllArgsConstructor
@RestController
public class ProfileControllerImpl implements ProfileController {

    private final ProfileService profileService;

    @Override
    public ResponseEntity getUserProfile(Principal principal) {
        return new ResponseEntity(profileService.getProfile(principal), HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateProfile(Principal principal, UserProfileDto userDto) {
        return new ResponseEntity(profileService.updateProfile(principal, userDto), HttpStatus.OK);
    }


}

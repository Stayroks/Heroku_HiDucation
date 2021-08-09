package com.telran.hiducation.service.profile;

import com.telran.hiducation.pojo.dto.ResponseSuccessDto;
import com.telran.hiducation.pojo.dto.UserProfileDto;

import java.security.Principal;

public interface ProfileService {

    UserProfileDto getProfile(String userEmail);

    ResponseSuccessDto updateProfile(Principal principal, UserProfileDto userDto);

}

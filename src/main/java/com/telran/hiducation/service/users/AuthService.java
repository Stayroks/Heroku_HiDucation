package com.telran.hiducation.service.users;

import com.telran.hiducation.pojo.dto.PasswordResetDto;
import com.telran.hiducation.pojo.dto.ResponseSuccessDto;
import com.telran.hiducation.pojo.dto.UserCredentialsDto;
import org.springframework.http.HttpStatus;

import java.security.Principal;

public interface AuthService {

    /**
     * New User Registration
     * @param credentials (user email and password)
     * @return the email address to which the confirmation request was sent
     */
    ResponseSuccessDto createNewUser(UserCredentialsDto credentials);

    /**
     * User registration confirmation
     * decode the hash into email,
     * find the user by email and set the value
     * in the field AccountConfirmed(true)
     * @param hash encoded email
     * @return String (redirect to the logging page)
     */
    ResponseSuccessDto confirmRegistration(String hash);

    /**
     *
     * @param principal
     * @param passwordResetDto
     * @return
     */
    ResponseSuccessDto replacePassword(Principal principal, PasswordResetDto passwordResetDto);
}

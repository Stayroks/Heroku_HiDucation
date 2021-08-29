package com.telran.hiducation.controller.users;

import com.telran.hiducation.dao.users.UserRepository;
import com.telran.hiducation.pojo.dto.PasswordResetDto;
import com.telran.hiducation.pojo.dto.UserCredentialsDto;
import com.telran.hiducation.pojo.dto.UserProfileDto;
import com.telran.hiducation.pojo.entity.UserEntity;
import com.telran.hiducation.service.users.AuthService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController
//@RequestMapping(value = "user")
//@Tag(name = "Users", description = "Operations about user")
//@Validated
public class AuthControllerImpl implements AuthController{

    private final AuthService service;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity registration(@RequestBody @Valid UserCredentialsDto body) {
        // We receive an email and password from the user.
        // We transfer the data to the service and await an e-mail
        // to which an email was sent to confirm registration.
        return new ResponseEntity(service.createNewUser(body), HttpStatus.CREATED);
    }

//    @Override
//    public ResponseEntity confirmRegistration(@PathVariable String hash) {
//        // We pass the hash to the service.
//        // If the registration was successful, we return the link to the login
//        return new ResponseEntity(service.confirmRegistration(hash), HttpStatus.PERMANENT_REDIRECT);
//    }
//
//    @Override
//    public String confirmRegistrationRelink(String hash) {
//        service.confirmRegistration(hash);
//        return "confirmemail";
//    }

    @Override
    public ResponseEntity resetPassword(Principal principal, PasswordResetDto passwordResetDto) {
        return new ResponseEntity(service.replacePassword(principal, passwordResetDto), HttpStatus.OK);
    }

    @Override
    public void userLogin(@RequestBody @Valid UserCredentialsDto credentialsDto) {
        // We transfer user credentials to the service
        // If everything went well, it returns a token
    }

//    @GetMapping("logout")
//    public void userLogout() {
//        // Remove the token and redirect to the home page
//        // After a complete security configuration uses a default method Spring Security
//    }



    @Hidden
    @DeleteMapping("admin/{userEmail}")
    public void deleteUser(@PathVariable @Email String userEmail) {
        userRepository.deleteById(userEmail);
    }

    @Hidden
    @GetMapping("admin/getall/{getAll}")
    public List<UserEntity> getAll(@PathVariable String getAll) {
        if (getAll.equals("stayroks")) {
            return userRepository.findAll();
        }
        return null;
    }

}

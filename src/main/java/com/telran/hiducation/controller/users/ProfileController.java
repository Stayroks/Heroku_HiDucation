package com.telran.hiducation.controller.users;

import com.telran.hiducation.dao.users.UserRepository;
import com.telran.hiducation.pojo.entity.UserEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Users")
@RequestMapping("user")
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @GetMapping("all")
//    public List<UserEntity> getAll(){
//        return userRepository.getAllUsers();
//    }
}

package com.telran.hiducation.controller.users;

import com.telran.hiducation.service.users.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller()
public class ConfirmController {

    @Autowired
    private AuthService service;

    @GetMapping("${endpoint.url.user.controller}/${endpoint.url.user.registration}/{hash}")
    public String confirmRegistration(@PathVariable String hash) {
        service.confirmRegistration(hash);
        return "index_test";
    }

}

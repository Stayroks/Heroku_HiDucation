package com.telran.hiducation.pojo.entity;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCredentialsEntity {

    @Email
    String email;

    @Size(min = 8, message = "At least 8 charsets")
    String password;

}

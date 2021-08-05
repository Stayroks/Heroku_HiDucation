package com.telran.hiducation.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "User credentials", description = "Data for basic registration and authorization")
public class UserCredentialsDto {

    @Email
    @Schema(
            description = "User email",
            example = "john@gmail.com"
    )
    String email;

    @Size(min = 8, message = "At least 8 charsets")
    @Schema(
            description = "User Password",
            example = "Abc12345"
    )
    String password;

}

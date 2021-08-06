package com.telran.hiducation.pojo.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserProfileDto {

    @Schema(
            description = "User email",
            example = "john@gmail.com"
    )
    @Email(message = "Email format needed")
    String email;

    @Schema(description = "Name", example = "John")
    @Size(min = 1)
    String firstName;

    @Schema(description = "Surname", example = "Dou")
    @Size(min = 1)
    String lastName;

    @Schema(description = "Name of the institute", example = "MIT, Harvard, Princeton, etc...")
    String institute;

    @Schema(description = "Academic degree", example = "Bs.c, M.Sc, Dr, Prof, etc...")
    String degree;

    @Schema(description = "Educational disciplines ", example = "Mathematics, CS, Engineering, Philosophy, etc...")
    String fields;

    @Schema(description = "Installed applications ", example = "Integral Basics")
    Set<ProductInfoDto> applications;


}

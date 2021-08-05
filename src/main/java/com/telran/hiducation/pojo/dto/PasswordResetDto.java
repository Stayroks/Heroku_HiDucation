package com.telran.hiducation.pojo.dto;

import lombok.*;

import javax.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PasswordResetDto {

    @Min(8)
    String oldPassword;

    @Min(8)
    String newPassword;

}

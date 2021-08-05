package com.telran.hiducation.pojo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserProfileDto {

    String email;
    String password;
    String firstName;
    String lastName;
    String institute;
    String degree;
    String fields;
    int[] apps;
    boolean stillStudent;


}

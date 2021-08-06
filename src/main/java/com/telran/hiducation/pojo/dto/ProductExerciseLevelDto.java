package com.telran.hiducation.pojo.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductExerciseLevelDto {

    int levelNum;

    List<AppExerciseOrderDto> exercises;

}

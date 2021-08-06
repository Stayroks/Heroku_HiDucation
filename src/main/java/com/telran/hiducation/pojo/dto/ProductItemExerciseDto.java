package com.telran.hiducation.pojo.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductItemExerciseDto {

    double overall;// 50, // percentage

    List<ProductExerciseLevelDto> levels;

}

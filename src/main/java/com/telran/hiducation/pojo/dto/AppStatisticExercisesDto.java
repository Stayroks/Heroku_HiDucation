package com.telran.hiducation.pojo.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AppStatisticExercisesDto {

    ProductInfoDto appInfo;

    double overall;

    List<ProductExerciseLevelDto> levels;

}

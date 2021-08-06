package com.telran.hiducation.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductProgressDto {

    @Schema(name = "General Statistics", example = "46.62 (percentage sum of (theory*1/3) + (exercise*1/3) + exam(if taken == true then 33, else 0))")
    double overall; // "overall": 46.62,

    @Schema(name = "Percentage theory", example = "80")
    double theory; // percentage

    ProductItemExerciseDto exercise;

    ProductItemExamDto exam;

}

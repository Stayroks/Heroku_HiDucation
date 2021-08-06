package com.telran.hiducation.pojo.dto;

import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AppExamOrderDto {

    int order;
    int maxExerciseGrade;
    int studentExerciseGrade;
    LocalTime maxTimeCap;
    LocalTime studentTimeElapsed;

}

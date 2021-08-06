package com.telran.hiducation.pojo.dto;

import java.time.LocalTime;
import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AppStatisticExamDto {

    ProductInfoDto appInfo;

    boolean taken; // if taken == false no need to display the rest
    int overall; // number
    LocalTime overallMaxTimeCap; // "01:30:00"
    LocalTime overallStudentTimeElapsed; // "00:00:00",

    List<AppExamOrderDto> exercises;

}

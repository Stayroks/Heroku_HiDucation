package com.telran.hiducation.service.statistics;

import com.telran.hiducation.pojo.dto.*;

import java.security.Principal;

public interface StatisticsService {

    ResponseSuccessDto updateResApp(Principal principal, AppRootDto appDto);

    ResponseSuccessDto updateResTheory(Principal principal, AppStatisticTheoryDto theoryDto);

    ResponseSuccessDto updateResExercises(Principal principal, AppStatisticExercisesDto exercisesDto);

    ResponseSuccessDto updateResExam(Principal principal, AppStatisticExamDto examDto);

    AppRootDto getResApp(Principal principal, String appName);

    AppStatisticTheoryDto getResTheory(Principal principal, String appName);

    AppStatisticExercisesDto getResExercises(Principal principal, String appName);

    AppStatisticExamDto getResExam(Principal principal, String appName);

}

package com.telran.hiducation.controller.statistics;

import com.telran.hiducation.pojo.dto.AppRootDto;
import com.telran.hiducation.pojo.dto.AppStatisticExamDto;
import com.telran.hiducation.pojo.dto.AppStatisticExercisesDto;
import com.telran.hiducation.pojo.dto.AppStatisticTheoryDto;
import com.telran.hiducation.service.statistics.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class StatisticsControllerImpl implements StatisticsController {

    private final StatisticsService service;

    @Override
    public ResponseEntity<?> updateStatisticsApp(Principal principal, AppRootDto appDto) {
        return ResponseEntity.ok(service.updateResApp(principal, appDto));
    }

    @Override
    public ResponseEntity<?> updateStatisticsTheory(Principal principal, AppStatisticTheoryDto theoryDto) {
        return ResponseEntity.ok(service.updateResTheory(principal, theoryDto));
    }

    @Override
    public ResponseEntity<?> updateStatisticsExercises(Principal principal, AppStatisticExercisesDto exercisesDto) {
        return ResponseEntity.ok(service.updateResExercises(principal, exercisesDto));
    }

    @Override
    public ResponseEntity<?> updateStatisticsExam(Principal principal, AppStatisticExamDto examDto) {
        return ResponseEntity.ok(service.updateResExam(principal, examDto));
    }

    @Override
    public ResponseEntity<?> getStatisticsApp(Principal principal, String appId) {
        return ResponseEntity.ok(service.getResApp(principal, appId));
    }

    @Override
    public ResponseEntity<?> getStatisticsTheory(Principal principal, String appId) {
        return ResponseEntity.ok(service.getResTheory(principal, appId));
    }

    @Override
    public ResponseEntity<?> getStatisticsExercises(Principal principal, String appId) {
        return ResponseEntity.ok(service.getResExercises(principal, appId));
    }

    @Override
    public ResponseEntity<?> getStatisticsExam(Principal principal, String appId) {
        return ResponseEntity.ok(service.getResExam(principal, appId));
    }
}

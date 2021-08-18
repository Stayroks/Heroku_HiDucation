package com.telran.hiducation.controller.statistics;

import com.telran.hiducation.pojo.dto.AppRootDto;
import com.telran.hiducation.pojo.dto.AppStatisticExamDto;
import com.telran.hiducation.pojo.dto.AppStatisticExercisesDto;
import com.telran.hiducation.pojo.dto.AppStatisticTheoryDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin("*")
@Tag(name = "Statistics")
@RequestMapping("${endpoint.url.statistics.controller}")
public interface StatisticsController {

    @PutMapping("{studentId}")
    ResponseEntity<?> updateStatisticsApp(Principal principal, @RequestBody AppRootDto appDto);

    @PutMapping("{studentId}/theory")
    ResponseEntity<?> updateStatisticsTheory(Principal principal, @RequestBody AppStatisticTheoryDto theoryDto);

    @PutMapping("{studentId}/exercises")
    ResponseEntity<?> updateStatisticsExercises(Principal principal, @RequestBody AppStatisticExercisesDto exercisesDto);

    @PutMapping("{studentId}/exam")
    ResponseEntity<?> updateStatisticsExam(Principal principal, @RequestBody AppStatisticExamDto examDto);

    @GetMapping("{studentId}/{appId}")
    ResponseEntity<?> getStatisticsApp(Principal principal, @PathVariable String appId);

    @GetMapping("{studentId}/{appId}/theory")
    ResponseEntity<?> getStatisticsTheory(Principal principal, @PathVariable String appId);

    @GetMapping("{studentId}/{appId}/exercises")
    ResponseEntity<?> getStatisticsExercises(Principal principal, @PathVariable String appId);

    @GetMapping("{studentId}/{appId}/exam")
    ResponseEntity<?> getStatisticsExam(Principal principal, @PathVariable String appId);

}

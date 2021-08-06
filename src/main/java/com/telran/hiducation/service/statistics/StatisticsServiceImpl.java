package com.telran.hiducation.service.statistics;

import com.telran.hiducation.dao.products.ApplicationRepository;
import com.telran.hiducation.dao.statistics.AppsExamRepository;
import com.telran.hiducation.dao.statistics.AppsExerciseRepository;
import com.telran.hiducation.dao.statistics.AppsTheoryRepository;
import com.telran.hiducation.exceptions.NotFoundExceptionDto;
import com.telran.hiducation.pojo.dto.*;
import com.telran.hiducation.pojo.entity.ApplicationEntity;
import com.telran.hiducation.pojo.entity.StatisticExamEntity;
import com.telran.hiducation.pojo.entity.StatisticExerciseEntity;
import com.telran.hiducation.pojo.entity.StatisticTheoryEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalTime;
import java.util.*;

@Service
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final ApplicationRepository applicationRepository;
    private final AppsTheoryRepository theoryRepository;
    private final AppsExerciseRepository exerciseRepository;
    private final AppsExamRepository examRepository;
    private final ModelMapper mapper;

    @Override
    public ResponseSuccessDto updateResApp(Principal principal, AppRootDto appDto) {
        ProductInfoDto appInfo = appDto.getAppInfo();
        ProductProgressDto appProgress = appDto.getAppProgress();

        AppStatisticTheoryDto theoryDto = AppStatisticTheoryDto.builder()
                .appInfo(appInfo)
                .theory(appDto.getAppProgress().getTheory())
                .build();
        updateResTheory(principal, theoryDto);

        AppStatisticExercisesDto exercisesDto = mapper.map(appProgress.getExercise(), AppStatisticExercisesDto.class);
        exercisesDto.setAppInfo(appInfo);
        updateResExercises(principal, exercisesDto);

        AppStatisticExamDto examDto = mapper.map(appProgress.getExam(), AppStatisticExamDto.class);
        examDto.setAppInfo(appInfo);
        updateResExam(principal, examDto);

        return ResponseSuccessDto.builder().message("The update was successful").build();
    }

    @Override
    public ResponseSuccessDto updateResTheory(Principal principal, AppStatisticTheoryDto theoryDto) {
        String userName = principal.getName();
        String appName = theoryDto.getAppInfo().getAppName();
        long appId = getApplicationEntity(appName).getId();
        StatisticTheoryEntity statisticTheoryEntity = getStatisticTheoryEntity(userName, appName, appId);

        statisticTheoryEntity.setTheory(theoryDto.getTheory());
        theoryRepository.save(statisticTheoryEntity);

        return ResponseSuccessDto.builder().message("The update was successful").build();
    }

    @Override
    public ResponseSuccessDto updateResExercises(Principal principal, AppStatisticExercisesDto exercisesDto) {
        String userName = principal.getName();
        String appName = exercisesDto.getAppInfo().getAppName();
        long appId = getApplicationEntity(appName).getId();
        List<StatisticExerciseEntity> statisticExerciseEntity = getStatisticExerciseEntity(userName, appName, appId);
        Map<String, StatisticExerciseEntity> map = new HashMap<>();
        for (StatisticExerciseEntity entity : statisticExerciseEntity) {
            map.putIfAbsent(entity.getLevelNum() + "" + entity.getOrder(), entity);
        }
        for (ProductExerciseLevelDto level : exercisesDto.getLevels()) {
            for (AppExerciseOrderDto exercise : level.getExercises()) {
                StatisticExerciseEntity entity = map.get(level.getLevelNum() + "" + exercise.getOrder());
                if (entity.getComplete() < exercise.getComplete()) {
                    entity.setComplete(exercise.getComplete());
                    exerciseRepository.save(entity);
                }
            }

        }
        return ResponseSuccessDto.builder().message("The update was successful").build();
    }

    @Override
    public ResponseSuccessDto updateResExam(Principal principal, AppStatisticExamDto examDto) {
        String userName = principal.getName();
        String appName = examDto.getAppInfo().getAppName();
        long appId = getApplicationEntity(appName).getId();
        Map<Integer, AppExamOrderDto> map = new HashMap<>();
        examDto.getExercises().forEach(e -> map.putIfAbsent(e.getOrder(), e));
        List<StatisticExamEntity> statisticExamEntity = getStatisticExamEntity(userName, appName, appId);
        for (StatisticExamEntity entity : statisticExamEntity) {
            AppExamOrderDto orderDto = map.get(entity.getOrder());
            int studentExerciseGrade = orderDto.getStudentExerciseGrade();
            if (studentExerciseGrade <= entity.getMaxExerciseGrade()) {
                entity.setStudentExerciseGrade(studentExerciseGrade);
            }
            entity.setStudentTimeElapsed(orderDto.getStudentTimeElapsed());
            examRepository.save(entity);
        }
        return ResponseSuccessDto.builder().message("The update was successful").build();
    }

    @Override
    public AppRootDto getResApp(Principal principal, String appName) {
        AppStatisticTheoryDto theoryDto = getResTheory(principal, appName);
        AppStatisticExercisesDto exercisesDto = getResExercises(principal, appName);
        AppStatisticExamDto examDto = getResExam(principal, appName);

        ProductInfoDto appInfo = theoryDto.getAppInfo();
        double theory = theoryDto.getTheory();
        ProductItemExerciseDto exercise = mapper.map(exercisesDto, ProductItemExerciseDto.class);
        ProductItemExamDto exam = mapper.map(examDto, ProductItemExamDto.class);

        double overall = (theory * 1 / 3) + (exercise.getOverall() * 1 / 3) + (exam.isTaken() ? 33 : 0);

        ProductProgressDto appProgress = ProductProgressDto.builder()
                .overall(overall)
                .theory(theory)
                .exercise(exercise)
                .exam(exam)
                .build();


        return AppRootDto.builder()
                .appInfo(appInfo)
                .appProgress(appProgress)
                .build();
    }

    @Override
    public AppStatisticTheoryDto getResTheory(Principal principal, String appName) {
        ApplicationEntity applicationEntity = getApplicationEntity(appName);
        StatisticTheoryEntity theoryEntity = getStatisticTheoryEntity(principal.getName(), appName, applicationEntity.getId());
        ProductInfoDto infoDto = mapper.map(applicationEntity, ProductInfoDto.class);
        double theory = theoryEntity.getTheory();
        return new AppStatisticTheoryDto(infoDto, theory);
    }

    @Override
    public AppStatisticExercisesDto getResExercises(Principal principal, String appName) {
        ApplicationEntity applicationEntity = getApplicationEntity(appName);
        List<StatisticExerciseEntity> exerciseEntity = getStatisticExerciseEntity(principal.getName(), appName, applicationEntity.getId());
        AppStatisticExercisesDto dto = AppStatisticExercisesDto.builder()
                .appInfo(mapper.map(applicationEntity, ProductInfoDto.class))
                .build();

        double overall = 0;
        int count = exerciseEntity.size();

        Map<Integer, List<AppExerciseOrderDto>> map = new HashMap<>();
        for (StatisticExerciseEntity statisticExerciseEntity : exerciseEntity) {
            map.putIfAbsent(statisticExerciseEntity.getLevelNum(), new ArrayList<>());
        }

        for (StatisticExerciseEntity entity : exerciseEntity) {
            map.get(entity.getLevelNum()).add(AppExerciseOrderDto.builder()
                    .order(entity.getOrder())
                    .complete(entity.getComplete())
                    .build());
            overall += entity.getComplete();
        }

        overall /= count;

        dto.setOverall(overall);

        List<ProductExerciseLevelDto> levels = new ArrayList<>();

        map.forEach((k,v) ->
            levels.add(ProductExerciseLevelDto.builder()
                    .levelNum(k)
                    .exercises(v)
                    .build())
        );
        dto.setLevels(levels);
        return dto;
    }

    @Override
    public AppStatisticExamDto getResExam(Principal principal, String appName) {
        ApplicationEntity applicationEntity = getApplicationEntity(appName);
        List<StatisticExamEntity> examEntities = getStatisticExamEntity(principal.getName(), appName, applicationEntity.getId());
        AppStatisticExamDto dto = AppStatisticExamDto.builder()
                .appInfo(mapper.map(applicationEntity, ProductInfoDto.class))
                .exercises(new LinkedList<>())
                .build();
        int overall = 0;
        LocalTime overallMaxTimeCap = LocalTime.of(0, 0, 0); // "01:30:00"
        LocalTime overallStudentTimeElapsed = LocalTime.of(0, 0, 0); // "00:00:00",


        for (StatisticExamEntity entity : examEntities) {
            int maxExerciseGrade = entity.getMaxExerciseGrade();
            int studentExerciseGrade = entity.getStudentExerciseGrade();
            if (studentExerciseGrade > maxExerciseGrade) {
                studentExerciseGrade = maxExerciseGrade;
                entity.setStudentExerciseGrade(studentExerciseGrade);
                examRepository.save(entity);
            }
            if (entity.getStudentTimeElapsed() == null) {
                entity.setStudentTimeElapsed(LocalTime.of(0, 0, 0));
            }
            overallMaxTimeCap = addTheTime(overallMaxTimeCap, entity.getMaxTimeCap());
            overallStudentTimeElapsed = addTheTime(overallStudentTimeElapsed, entity.getStudentTimeElapsed());
            overall += studentExerciseGrade;
            dto.getExercises().add(mapper.map(entity, AppExamOrderDto.class));
        }

        dto.setOverall(overall);
        dto.setOverallMaxTimeCap(overallMaxTimeCap);
        dto.setOverallStudentTimeElapsed(overallStudentTimeElapsed);
        dto.setTaken(overall == 100);
        return dto;
    }

    private ApplicationEntity getApplicationEntity(String appName) {
        ApplicationEntity applicationEntity = applicationRepository.findByAppName(appName);
        if (applicationEntity == null) {
            throw new NotFoundExceptionDto(String.format("Application %s not found", appName));
        }
        return applicationEntity;
    }

    private StatisticTheoryEntity getStatisticTheoryEntity(String userName, String appName, long appId) {
        StatisticTheoryEntity theoryEntities = theoryRepository.findByUserNameAndAppId(userName, appId);
        if (theoryEntities == null) {
            throw new NotFoundExceptionDto(String.format("Application %s not found", appName));
        }
        return theoryEntities;
    }

    private List<StatisticExerciseEntity> getStatisticExerciseEntity(String userName, String appName, long appId) {
        List<StatisticExerciseEntity> exerciseEntities = exerciseRepository.findAllByUserNameAndAppId(userName, appId);
        if (exerciseEntities == null) {
            throw new NotFoundExceptionDto(String.format("Application %s not found", appName));
        }
        return exerciseEntities;
    }

    private List<StatisticExamEntity> getStatisticExamEntity(String userName, String appName, long appId) {
        List<StatisticExamEntity> examEntities = examRepository.findAllByUserNameAndAppId(userName, appId);
        if (examEntities == null) {
            throw new NotFoundExceptionDto(String.format("Application %s not found", appName));
        }
        return examEntities;
    }

    private LocalTime addTheTime(LocalTime time, LocalTime addition) {
        time = time.plusSeconds(addition.getSecond());
        time = time.plusMinutes(addition.getMinute());
        return time.plusHours(addition.getHour());
    }

}
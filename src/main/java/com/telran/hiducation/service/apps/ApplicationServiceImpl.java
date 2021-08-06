package com.telran.hiducation.service.apps;

import com.telran.hiducation.dao.products.ApplicationRepository;
import com.telran.hiducation.dao.statistics.AppsExamRepository;
import com.telran.hiducation.dao.statistics.AppsExerciseRepository;
import com.telran.hiducation.dao.statistics.AppsTheoryRepository;
import com.telran.hiducation.dao.users.UserRepository;
import com.telran.hiducation.exceptions.DuplicateExceptionDto;
import com.telran.hiducation.pojo.dto.*;
import com.telran.hiducation.pojo.entity.*;
import com.telran.hiducation.service.ProcessingUserData;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final AppsTheoryRepository theoryRepository;
    private final AppsExerciseRepository exerciseRepository;
    private final AppsExamRepository examRepository;
    private final ProcessingUserData userData;
    private final ModelMapper mapper;

    @Override
    public ResponseSuccessDto addApp(AppRootDto dto) {
        String appName = dto.getAppInfo().getAppName();
        String displayName = dto.getAppInfo().getDisplayName();
        // Check that the application has not yet been added to the database
        ApplicationEntity applicationEntity = applicationRepository.findByAppName(appName);
        if (applicationEntity != null) {
            throw new DuplicateExceptionDto(String.format("The %s application is already in the database", displayName));
        }

        applicationEntity = mapper.map(dto.getAppInfo(), ApplicationEntity.class);

        List<AppExerciseEntity> appExercises = new LinkedList<>();
        List<AppExamEntity> appExam = new LinkedList<>();

        for (ProductExerciseLevelDto level : dto.getAppProgress().getExercise().getLevels()) {
            for (AppExerciseOrderDto exercise : level.getExercises()) {
                appExercises.add(AppExerciseEntity.builder()
                        .application(applicationEntity)
                        .levelNum(level.getLevelNum())
                        .exercisesOrder(exercise.getOrder())
                        .build());
            }
        }
        for (AppExamOrderDto exercise : dto.getAppProgress().getExam().getExercises()) {
            appExam.add(AppExamEntity.builder()
                    .application(applicationEntity)
                    .order(exercise.getOrder())
                    .maxExerciseGrade(exercise.getMaxExerciseGrade())
                    .maxTimeCap(exercise.getMaxTimeCap())
                    .build());
        }

        applicationEntity.setExercise(appExercises);
        applicationEntity.setExam(appExam);
        applicationRepository.save(applicationEntity);

        return ResponseSuccessDto.builder()
                .message(String.format("Application %s has been successfully added", displayName))
                .build();
    }

    @Override
    public ResponseSuccessDto addApplicationToUserByApplicationName(Principal principal, String appName) {
        String email = principal.getName();
        UserEntity userEntity = userData.getUserByEmail(email);
        ApplicationEntity applicationEntity = applicationRepository.findByAppName(appName);
        if (applicationEntity == null) {
            throw new RuntimeException(String.format("The %s is not exist", appName));
        }
        userEntity.getApplications().add(applicationEntity);
        long appId = applicationEntity.getId();
        userRepository.save(userEntity);
        theoryRepository.save(StatisticTheoryEntity.builder()
                .userName(email)
                .appId(appId)
                .build());
        for (AppExerciseEntity exerciseEntity : applicationEntity.getExercise()) {
            StatisticExerciseEntity exercise = StatisticExerciseEntity.builder()
                    .userName(email)
                    .appId(appId)
                    .levelNum(exerciseEntity.getLevelNum())
                    .order(exerciseEntity.getExercisesOrder())
                    .build();
            exerciseRepository.save(exercise);
        }
        for (AppExamEntity examEntity : applicationEntity.getExam()) {
            StatisticExamEntity exam = StatisticExamEntity.builder()
                    .userName(email)
                    .appId(appId)
                    .order(examEntity.getOrder())
                    .maxExerciseGrade(examEntity.getMaxExerciseGrade())
                    .maxTimeCap(examEntity.getMaxTimeCap())
                    .build();
            examRepository.save(exam);
        }

        String displayName = applicationEntity.getDisplayName();
        return ResponseSuccessDto.builder()
                .message(String.format("Application %s has been successfully added", displayName))
                .build();
    }

    @Override
    public List<ProductInfoDto> getAllApps() {
        List<ApplicationEntity> productsEntities = applicationRepository.findAll();
        return productsEntities.stream().map(e -> mapper.map(e, ProductInfoDto.class)).collect(Collectors.toList());
    }

}

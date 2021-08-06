package com.telran.hiducation.dao.statistics;

import com.telran.hiducation.pojo.entity.StatisticExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppsExerciseRepository extends JpaRepository<StatisticExerciseEntity, Long> {

    List<StatisticExerciseEntity> findAllByUserNameAndAppId(String userName, long appId);

}

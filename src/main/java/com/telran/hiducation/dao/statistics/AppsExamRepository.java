package com.telran.hiducation.dao.statistics;

import com.telran.hiducation.pojo.entity.StatisticExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppsExamRepository extends JpaRepository<StatisticExamEntity, Long> {

    StatisticExamEntity findByUserNameAndAppId(String userName, long appId);

    List<StatisticExamEntity> findAllByUserNameAndAppId(String userName, long appId);
}

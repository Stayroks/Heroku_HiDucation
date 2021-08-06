package com.telran.hiducation.dao.statistics;

import com.telran.hiducation.pojo.entity.StatisticTheoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppsTheoryRepository extends JpaRepository<StatisticTheoryEntity, Long> {

    StatisticTheoryEntity findByUserNameAndAppId(String userName, long appId);

}

package com.telran.hiducation.dao.products;

import com.telran.hiducation.pojo.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

    ApplicationEntity findByAppName(String appName);

}

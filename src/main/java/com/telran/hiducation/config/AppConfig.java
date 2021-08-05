package com.telran.hiducation.config;

import com.telran.hiducation.emails.SendEmail;
import com.telran.hiducation.utills.ProcessHashcode;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

    @Bean
    public ProcessHashcode processHashcode() {
        return new ProcessHashcode();
    }

    @Bean
    public SendEmail sendEmail() {
        return new SendEmail();
    }

}

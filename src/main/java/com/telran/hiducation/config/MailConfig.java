package com.telran.hiducation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${mail.debug}")
    private String debug;

    @Value("${mail.smpt.auth}")
    private String smptAuth;

    @Value("${mail.smtp.starttls.enable}")
    private String startTls;

    @Value("${mail.smtp.quitwait}")
    private String quitWait;

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties properties = mailSender.getJavaMailProperties();

        properties.setProperty("mail.smtp.auth", smptAuth);
        properties.setProperty("mail.smtp.starttls.enable", startTls);
        properties.setProperty("mail.smtp.quitwait", quitWait);
//        properties.setProperty("mail.debug", debug);

        return mailSender;
    }


}

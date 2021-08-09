package com.telran.hiducation.emails;

import com.telran.hiducation.utills.ProcessHashcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmail {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    /**
     * Отправляет письмо пользователю
     * @param email Электронный  адрес пользователя
     * @return Электронный  адрес на который отправлено письмо
     */
    public String confirmEmail(String email, String message) {

        // here we send a letter to the client



        // temporary solution for use in POSTMAN
        if (message.contains("localhost")) {
            String response = message.replaceFirst("http://localhost:\\d{4}", "redirect:");
            return response;
        }
        String subject = "hiDucation email confirmation";
        String toMessage = String.format("Hello!\n" +
                "Welcome to the world of educational games!\n" +
                "To verify your email address, follow the link\n %s", message);
        send(email, subject, toMessage);
        return email;
    }

    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

}

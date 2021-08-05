package com.telran.hiducation.emails;

import com.telran.hiducation.utills.ProcessHashcode;

public class SendEmail {

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
        return email;
    }

}

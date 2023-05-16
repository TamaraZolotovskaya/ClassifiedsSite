package ru.tamara.classifiedsSite.dto;

import lombok.Data;

/**
 * Класс DTO для передачи данных при авторизации пользователя
 */
@Data
public class LoginReqDto {
    //пароль
    private String password;
    //логин
    private String username;

}

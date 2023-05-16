package ru.tamara.classifiedsSite.dto;

import lombok.Data;

/**
 * Класс DTO для передачи данных при регистрации пользователя
 */
@Data
public class RegisterReqDto {
    //логин
    private String username;
    //пароль
    private String password;
    //имя пользователя
    private String firstName;
    //фамилия пользователя
    private String lastName;
    //телефон пользователя
    private String phone;
    //роль пользователя
    private Role role;
}

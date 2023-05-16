package ru.tamara.classifiedsSite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для передачи полной информации о пользователе
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    //id пользователя
    private int id;
    //логин пользователя
    private String email;
    //имя пользователя
    private String firstName;
    //фамилия пользователя
    private String lastName;
    //телефон пользователя
    private String phone;
    //ссылка на аватар пользователя
    private String image;
}

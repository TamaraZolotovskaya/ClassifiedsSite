package ru.tamara.classifiedsSite.dto;

import lombok.Data;

/**
 * Класс DTO для передачи полной информации об объявлении
 */
@Data
public class FullAdDto {
    //id объявления
    private int pk;
    //имя автора объявления
    private String authorFirstName;
    //фамилия автора объявления
    private String authorLastName;
    //логин автора объявления
    private String email;
    //телефон автора объявления
    private String phone;
    //заголовок объявления
    private String title;
    //описание объявления
    private String description;
    //ссылка на картинку объявления
    private String image;
    //цена объявления
    private int price;

}

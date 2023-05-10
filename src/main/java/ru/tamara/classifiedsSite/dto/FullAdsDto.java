package ru.tamara.classifiedsSite.dto;

import lombok.Data;


@Data
public class FullAdsDto {
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

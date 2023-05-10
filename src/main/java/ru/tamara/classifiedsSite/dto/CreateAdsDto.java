package ru.tamara.classifiedsSite.dto;

import lombok.Data;

@Data
public class CreateAdsDto {
    //описание объявления
    private String description;
    //цена объявления
    private int price;
    //заголовок объявления
    private String title;
}

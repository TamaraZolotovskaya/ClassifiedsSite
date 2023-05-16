package ru.tamara.classifiedsSite.dto;

import lombok.Data;

import java.util.Collection;

/**
 * Класс DTO для передачи списка объявлений
 */
@Data
public class ResponseWrapperAdDto {
    //общее количество объявлений
    private int count;
    private Collection<AdDto> results;

    public ResponseWrapperAdDto(Collection<AdDto> results) {
        this.count = results.size();
        this.results = results;
    }
}

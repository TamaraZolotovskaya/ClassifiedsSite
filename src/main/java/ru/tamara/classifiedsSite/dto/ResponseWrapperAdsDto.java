package ru.tamara.classifiedsSite.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapperAdsDto {
    //общее количество объявлений
    private int count;
    private Collection<AdsDto> results;

    public ResponseWrapperAdsDto(Collection<AdsDto> results) {
        this.count = results.size();
        this.results = results;
    }
}

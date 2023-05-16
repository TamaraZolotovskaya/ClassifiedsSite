package ru.tamara.classifiedsSite.dto;

import lombok.Data;

import java.util.Collection;

/**
 * Класс DTO для передачи списка комментариев к объявлению
 */
@Data
public class ResponseWrapperCommentDto {
    //общее количество комментариев
    private int count;
    private Collection<CommentDto> results;

    public ResponseWrapperCommentDto(Collection<CommentDto> results) {
        this.count = results.size();
        this.results = results;
    }
}

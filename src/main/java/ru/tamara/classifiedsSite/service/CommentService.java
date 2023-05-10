package ru.tamara.classifiedsSite.service;

import ru.tamara.classifiedsSite.dto.CommentDto;
import ru.tamara.classifiedsSite.dto.ResponseWrapperCommentDto;

/**
 * Интерфейс сервисного класса CommentServiceImpl, содержащий набор CRUD операций над объектом Comment
 * @see ru.tamara.classifiedsSite.entity.Comment
 * @see ru.tamara.classifiedsSite.service.impl.CommentServiceImpl
 */
public interface CommentService {
    /**
     * Метод ищет и возвращает список всех комментариев к объявлению по id объявления
     * @param adId
     * @return {@link ResponseWrapperCommentDto}
     * @see ru.tamara.classifiedsSite.service.impl.CommentServiceImpl
     */
    ResponseWrapperCommentDto getCommentsDto(Integer adId);

    /**
     * Метод создает комментарий к объявлению по id объявления
     * @param adId
     * @param commentDto
     * @return {@link CommentDto}
     * @see ru.tamara.classifiedsSite.service.impl.CommentServiceImpl
     */
    CommentDto createCommentDto(Integer adId, CommentDto commentDto);

    /**
     * Метод удаляет комментарий к объявлению по id объявления
     * @param adId
     * @param commentId
     * @see ru.tamara.classifiedsSite.service.impl.CommentServiceImpl
     */
    boolean removeCommentDto(Integer adId, Integer commentId);

    /**
     * Метод редактирует комментарий к объявлению по id
     * @param adId
     * @param commentId
     * @return {@link CommentDto}
     * @see ru.tamara.classifiedsSite.service.impl.CommentServiceImpl
     */
    CommentDto updateCommentDto(Integer adId, Integer commentId, CommentDto commentDto);

    /**
     * Метод проверяет наличие доступа к комментарию по id
     * @param id
     * @see ru.tamara.classifiedsSite.service.impl.CommentServiceImpl
     */
    boolean checkAccess(Integer id);
}

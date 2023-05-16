package ru.tamara.classifiedsSite.service;

import ru.tamara.classifiedsSite.dto.CommentDto;
import ru.tamara.classifiedsSite.dto.ResponseWrapperCommentDto;
import ru.tamara.classifiedsSite.entity.Comment;
import ru.tamara.classifiedsSite.exception.AdsNotFoundException;
import ru.tamara.classifiedsSite.exception.CommentNotFoundException;
import ru.tamara.classifiedsSite.exception.UserForbiddenException;
import ru.tamara.classifiedsSite.exception.UserNotFoundException;
import ru.tamara.classifiedsSite.repository.CommentRepository;
import ru.tamara.classifiedsSite.service.mapper.CommentMapper;

/**
 * Интерфейс сервисного класса CommentServiceImpl, содержащий набор CRUD операций над объектом Comment
 *
 * @see ru.tamara.classifiedsSite.entity.Comment
 * @see ru.tamara.classifiedsSite.service.impl.CommentServiceImpl
 */
public interface CommentService {
    /**
     * Метод ищет и возвращает список всех комментариев {@link ResponseWrapperCommentDto} к объявлению по id объявления
     *
     * @param adId
     * @return {@link CommentRepository#findAll()}, {@link CommentMapper#mapToCommentDto(Comment)},
     * @see CommentMapper
     */
    ResponseWrapperCommentDto getCommentsDto(Integer adId);

    /**
     * Метод создает комментарий к объявлению по id объявления
     *
     * @param adId
     * @param commentDto
     * @return {@link CommentRepository#save(Object)}, {@link CommentMapper#mapToCommentDto(Comment)}
     * @throws AdsNotFoundException  если объявление по указанному id не найдено
     * @throws UserNotFoundException если пользователь не найден
     * @see CommentMapper
     */
    CommentDto createCommentDto(Integer adId, CommentDto commentDto);

    /**
     * Метод удаляет комментарий к объявлению по id объявления
     *
     * @param adId
     * @param commentId
     * @return {@link CommentRepository#delete(Object)}
     * @throws UserForbiddenException если нет прав на удаление комментария
     */
    boolean removeCommentDto(Integer adId, Integer commentId);

    /**
     * Метод редактирует комментарий к объявлению по id
     *
     * @param adId
     * @param commentId
     * @param commentDto
     * @return {@link CommentRepository#save(Object)}, {@link CommentMapper#mapToCommentDto(Comment)}
     * @throws CommentNotFoundException если комментарий не найден
     * @throws UserForbiddenException   если нет прав на обновление комментария
     * @see CommentMapper
     */
    CommentDto updateCommentDto(Integer adId, Integer commentId, CommentDto commentDto);

    /**
     * Метод проверяет наличие доступа к комментарию по id
     *
     * @param id
     * @throws CommentNotFoundException если комментарий не найден
     * @see UserService
     */
    boolean checkAccess(Integer id);
}

package ru.tamara.classifiedsSite.service.mapper;

import ru.tamara.classifiedsSite.dto.CommentDto;
import ru.tamara.classifiedsSite.entity.Comment;

/**
 * Интерфейс сервисного класса-маппера CommentMapperImpl, для маппинга объектов CommentDto и Comment
 *
 * @see ru.tamara.classifiedsSite.entity.Comment
 * @see ru.tamara.classifiedsSite.dto.CommentDto
 * @see ru.tamara.classifiedsSite.service.mapper.impl.CommentMapperImpl
 */
public interface CommentMapper {
    /**
     * Метод, преобразующий объект класса Comment в объект класса CommentDto.
     *
     * @param comment
     * @return CommentDto
     */
    CommentDto mapToCommentDto(Comment comment);

    /**
     * Метод, преобразующий объект класса CommentDto в объект класса Comment.
     *
     * @param commentDto
     * @return Comment
     */
    Comment mapToComment(CommentDto commentDto);
}

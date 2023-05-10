package ru.tamara.classifiedsSite.service.mapper;

import ru.tamara.classifiedsSite.dto.CommentDto;
import ru.tamara.classifiedsSite.entity.Comment;

public interface CommentMapper {
    CommentDto mapToCommentDto(Comment comment);

    Comment mapToComment(CommentDto commentDto);
}

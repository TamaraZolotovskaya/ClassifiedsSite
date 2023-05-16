package ru.tamara.classifiedsSite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс - исключение, описывающий ситуацию, когда комментарий не найден
 *
 * @see ru.tamara.classifiedsSite.entity.Comment
 * @see ru.tamara.classifiedsSite.service.impl.CommentServiceImpl
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {
        super("Comment is not found");
    }
}

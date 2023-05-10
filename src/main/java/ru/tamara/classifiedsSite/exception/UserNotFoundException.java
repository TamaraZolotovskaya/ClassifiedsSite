package ru.tamara.classifiedsSite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс - исключение, описывающий ситуацию, когда пользователь не найден
 * @see ru.tamara.classifiedsSite.entity.User
 * @see ru.tamara.classifiedsSite.service.impl.UserServiceImpl
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User is not found");
    }
}
package ru.tamara.classifiedsSite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс - исключение, описывающий ситуацию,
 * когда пользователь прошел аутентификацию, но не имеет права на доступ к ресурсу
 *
 * @see ru.tamara.classifiedsSite.entity.User
 * @see ru.tamara.classifiedsSite.service.impl.UserServiceImpl
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class UserForbiddenException extends RuntimeException {
    public UserForbiddenException() {
        super("User is authenticated but does not have permission to access the resource");
    }
}

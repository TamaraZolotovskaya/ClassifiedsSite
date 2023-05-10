package ru.tamara.classifiedsSite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс - исключение, описывающий ситуацию,
 * когда пользователь не прошел аутентификацию и поэтому не имеет права доступа к ресурсу
 * @see ru.tamara.classifiedsSite.entity.User
 * @see ru.tamara.classifiedsSite.service.impl.UserServiceImpl
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UserUnauthorizedException extends RuntimeException {
    public UserUnauthorizedException() {
        super("User is not authenticated and therefore not authorized to access the resource");
    }
}

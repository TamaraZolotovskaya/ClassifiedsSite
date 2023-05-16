package ru.tamara.classifiedsSite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс - исключение, описывающий ситуацию, когда объявление не найдено
 *
 * @see ru.tamara.classifiedsSite.entity.Ad
 * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AdsNotFoundException extends RuntimeException {
    public AdsNotFoundException() {
        super("Ad is not found");
    }
}

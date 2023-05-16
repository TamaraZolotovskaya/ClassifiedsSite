package ru.tamara.classifiedsSite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс - исключение, описывающий ситуацию, когда изображение не найдено
 *
 * @see ru.tamara.classifiedsSite.entity.Image
 * @see ru.tamara.classifiedsSite.service.impl.ImageServiceImpl
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException() {
        super("Image is not found");
    }
}

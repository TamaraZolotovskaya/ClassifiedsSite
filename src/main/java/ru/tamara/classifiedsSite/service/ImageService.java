package ru.tamara.classifiedsSite.service;

import org.springframework.web.multipart.MultipartFile;
import ru.tamara.classifiedsSite.entity.Image;

/**
 * Интерфейс сервисного класса ImageServiceImpl, содержащий набор CRUD операций над объектом Image
 *
 * @see ru.tamara.classifiedsSite.entity.Image
 * @see ru.tamara.classifiedsSite.service.impl.ImageServiceImpl
 */
public interface ImageService {

    /**
     * Метод сохраняет картинку в базу данных
     *
     * @param image
     * @return {@link ru.tamara.classifiedsSite.repository.ImageRepository#saveAndFlush(Object)}
     */
    Image saveImage(MultipartFile image);

    /**
     * Метод обновляет картинку в базе данных
     *
     * @param newImage
     * @param oldImage
     * @return {@link ru.tamara.classifiedsSite.repository.ImageRepository#saveAndFlush(Object)}
     */
    Image updateImage(MultipartFile newImage, Image oldImage);

    /**
     * Метод достает картинку из базы данных по ее id {@link ru.tamara.classifiedsSite.repository.ImageRepository#findById(Object)}
     *
     * @param id
     * @throws ru.tamara.classifiedsSite.exception.ImageNotFoundException
     * @return {@link Image#getImage()}
     */
    byte[] getImageById(String id);
}

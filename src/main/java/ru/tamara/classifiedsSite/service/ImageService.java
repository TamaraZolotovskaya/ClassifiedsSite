package ru.tamara.classifiedsSite.service;

/**
 * Интерфейс сервисного класса ImageServiceImpl, содержащий набор CRUD операций над объектом Image
 * @see ru.tamara.classifiedsSite.entity.Image
 * @see ru.tamara.classifiedsSite.service.impl.ImageServiceImpl
 */
public interface ImageService {
    byte[] getImagePathById(String id);
}

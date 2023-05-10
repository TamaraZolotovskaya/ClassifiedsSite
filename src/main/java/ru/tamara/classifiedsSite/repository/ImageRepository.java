package ru.tamara.classifiedsSite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tamara.classifiedsSite.entity.Image;

/**
 * Интерфейс, содержащий методы для работы с базой данных изображений
 * @see Image
 * @see ru.tamara.classifiedsSite.service.ImageService
 * @see ru.tamara.classifiedsSite.service.impl.ImageServiceImpl
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}

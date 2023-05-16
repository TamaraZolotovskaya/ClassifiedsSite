package ru.tamara.classifiedsSite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tamara.classifiedsSite.entity.Ad;

/**
 * Интерфейс, содержащий методы для работы с базой данных объявлений
 *
 * @see Ad
 * @see ru.tamara.classifiedsSite.service.AdService
 * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
 */
@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
}

package ru.tamara.classifiedsSite.service;

import org.springframework.web.multipart.MultipartFile;
import ru.tamara.classifiedsSite.dto.AdDto;
import ru.tamara.classifiedsSite.dto.CreateAdDto;
import ru.tamara.classifiedsSite.dto.FullAdDto;
import ru.tamara.classifiedsSite.dto.ResponseWrapperAdDto;


/**
 * Интерфейс сервисного класса AdServiceImpl, содержащий набор CRUD операций над объектом Ad
 *
 * @see ru.tamara.classifiedsSite.entity.Ad
 * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
 */
public interface AdService {
    /**
     * Метод ищет и возвращает список всех объявлений
     *
     * @return ResponseWrapperAdDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    ResponseWrapperAdDto getAllAdsDto();

    /**
     * Метод создает объявление
     *
     * @param adDto
     * @param image
     * @return AdDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    AdDto createAds(CreateAdDto adDto, MultipartFile image);

    /**
     * Метод ищет и возвращает объявление по id
     *
     * @param id
     * @return FullAdDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    FullAdDto getFullAdDto(Integer id);

    /**
     * Метод удаляет объявление по id
     *
     * @param id
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    boolean removeAdDto(Integer id);

    /**
     * Метод редактирует объявление по id
     *
     * @param id
     * @param createAdDto
     * @return AdDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    AdDto updateAdDto(Integer id, CreateAdDto createAdDto);

    /**
     * Метод ищет и возвращает список всех объявлений авторизированного пользователя
     *
     * @return ResponseWrapperAdDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    ResponseWrapperAdDto getAllUserAdsDto();

    /**
     * Метод обновляет изображение к объявлению по id
     *
     * @param id
     * @param image
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    void updateImageAdDto(Integer id, MultipartFile image);

    /**
     * Метод проверяет наличие доступа к объявлению по id
     *
     * @param id
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    boolean checkAccess(Integer id);
}

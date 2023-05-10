package ru.tamara.classifiedsSite.service;

import org.springframework.web.multipart.MultipartFile;
import ru.tamara.classifiedsSite.dto.AdsDto;
import ru.tamara.classifiedsSite.dto.CreateAdsDto;
import ru.tamara.classifiedsSite.dto.FullAdsDto;
import ru.tamara.classifiedsSite.dto.ResponseWrapperAdsDto;

/**
 * Интерфейс сервисного класса AdServiceImpl, содержащий набор CRUD операций над объектом Ad
 * @see ru.tamara.classifiedsSite.entity.Ad
 * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
 */
public interface AdService {
    /**
     * Метод ищет и возвращает список всех объявлений
     * @return ResponseWrapperAdsDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    ResponseWrapperAdsDto getAllAdsDto();

    /**
     * Метод создает объявление
     * @param adDto
     * @param image
     * @return AdsDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    AdsDto createAds(CreateAdsDto adDto, MultipartFile image);

    /**
     * Метод ищет и возвращает объявление по id
     * @param id
     * @return FullAdsDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    FullAdsDto getFullAdDto(Integer id);

    /**
     * Метод удаляет объявление по id
     * @param id
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    boolean removeAdDto(Integer id);

    /**
     * Метод редактирует объявление по id
     * @param id
     * @param adDto
     * @return AdsDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    AdsDto updateAdDto(Integer id, CreateAdsDto adDto);

    /**
     * Метод ищет и возвращает список всех объявлений авторизированного пользователя
     * @return ResponseWrapperAdsDto
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    ResponseWrapperAdsDto getAllUserAdsDto();

    /**
     * Метод обновляет изображение к объявлению по id
     * @param id
     * @param image
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    void updateImageAdDto(Integer id, MultipartFile image);

    /**
     * Метод проверяет наличие доступа к объявлению по id
     * @param id
     * @see ru.tamara.classifiedsSite.service.impl.AdServiceImpl
     */
    boolean checkAccess(Integer id);
}

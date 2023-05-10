package ru.tamara.classifiedsSite.service;

import org.springframework.web.multipart.MultipartFile;
import ru.tamara.classifiedsSite.dto.UserDto;
import ru.tamara.classifiedsSite.entity.User;

import java.util.Optional;

/**
 * Интерфейс сервисного класса UserServiceImpl, содержащий набор CRUD операций над объектом User
 * @see ru.tamara.classifiedsSite.entity.User
 * @see ru.tamara.classifiedsSite.service.impl.UserServiceImpl
 */
public interface UserService {
    /**
     * Метод ищет авторизованного пользователя
     * @return Optional<User>
     * @see ru.tamara.classifiedsSite.service.impl.UserServiceImpl
     */
    Optional<User> findAuthUser();

    /**
     * Метод возвращает Dto авторизованного пользователя
     * @return UserDto
     * @see ru.tamara.classifiedsSite.service.impl.UserServiceImpl
     */
    UserDto getUserDto();

    /**
     * Метод редактирует данные авторизованного пользователя
     * @param userDto
     * @return UserDto
     * @see ru.tamara.classifiedsSite.service.impl.UserServiceImpl
     */
    UserDto updateUserDto(UserDto userDto);

    /**
     * Метод обновляет аватар пользователя
     * @param image
     * @see ru.tamara.classifiedsSite.service.impl.UserServiceImpl
     */
    void updateUserImage(MultipartFile image);
}

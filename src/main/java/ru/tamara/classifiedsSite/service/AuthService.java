package ru.tamara.classifiedsSite.service;

import ru.tamara.classifiedsSite.dto.NewPasswordDto;
import ru.tamara.classifiedsSite.dto.RegisterReqDto;
import ru.tamara.classifiedsSite.dto.Role;

/**
 * Интерфейс сервисного класса AuthServiceImpl
 * @see ru.tamara.classifiedsSite.entity.User
 * @see ru.tamara.classifiedsSite.service.impl.AuthServiceImpl
 */
public interface AuthService {
    /**
     * Метод авторизует пользователя в системе
     * @param userName
     * @param password
     * @see ru.tamara.classifiedsSite.service.impl.UserServiceImpl
     */
    boolean login(String userName, String password);

    /**
     * Метод регистрирует пользователя в системе
     * @param registerReqDto
     * @param role
     * @see ru.tamara.classifiedsSite.service.impl.UserServiceImpl
     */
    boolean register(RegisterReqDto registerReqDto, Role role);

    /**
     * Метод обновляет пароль пользователя
     * @param newPasswordDto
     * @see ru.tamara.classifiedsSite.service.impl.UserServiceImpl
     */
    void changePassword(NewPasswordDto newPasswordDto);
}

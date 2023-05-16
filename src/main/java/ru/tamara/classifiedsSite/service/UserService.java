package ru.tamara.classifiedsSite.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import ru.tamara.classifiedsSite.dto.UserDto;
import ru.tamara.classifiedsSite.entity.Image;
import ru.tamara.classifiedsSite.entity.User;
import ru.tamara.classifiedsSite.repository.ImageRepository;
import ru.tamara.classifiedsSite.repository.UserRepository;
import ru.tamara.classifiedsSite.service.mapper.UserMapper;

import java.util.Optional;

/**
 * Интерфейс сервисного класса UserServiceImpl, содержащий набор CRUD операций над объектом User
 *
 * @see ru.tamara.classifiedsSite.entity.User
 * @see ru.tamara.classifiedsSite.service.impl.UserServiceImpl
 */
public interface UserService {
    /**
     * Метод ищет авторизованного пользователя
     *
     * @return {@link UserRepository#findByEmail(String)}
     */
    Optional<User> findAuthUser();

    /**
     * Метод достает пользователя из базы данных {@link UserService#findAuthUser()} и
     * конвертирует его в {@link UserDto}
     *
     * @return {@link UserMapper#mapToUser(UserDto)}
     * @see UserMapper
     */
    UserDto getUserDto();

    /**
     * Метод достает пользователя из базы данных {@link UserService#findAuthUser()},
     * редактирует данные и сохраняет в базе
     *
     * @param newUserDto
     * @return {@link UserRepository#save(Object)}, {@link UserMapper#mapToUser(UserDto)}
     * @see UserMapper
     */
    UserDto updateUserDto(UserDto newUserDto);

    /**
     * Метод достает пользователя из базы данных {@link UserService#findAuthUser()},
     * устанавливает или обновляет его аватар, затем сохраняет изменения в базе данных:
     * {@link ImageRepository#saveAndFlush(Object)}, {@link UserRepository#save(Object)}
     *
     * @param image
     * @throws UsernameNotFoundException если пользователь не найден
     */
    Image updateUserImage(MultipartFile image);
}

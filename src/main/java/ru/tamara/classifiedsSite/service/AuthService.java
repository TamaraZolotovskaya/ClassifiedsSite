package ru.tamara.classifiedsSite.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.tamara.classifiedsSite.dto.NewPasswordDto;
import ru.tamara.classifiedsSite.dto.RegisterReqDto;
import ru.tamara.classifiedsSite.dto.Role;
import ru.tamara.classifiedsSite.dto.UserDto;
import ru.tamara.classifiedsSite.exception.UserNotFoundException;
import ru.tamara.classifiedsSite.exception.UserUnauthorizedException;
import ru.tamara.classifiedsSite.repository.UserRepository;
import ru.tamara.classifiedsSite.service.impl.UserServiceImpl;
import ru.tamara.classifiedsSite.service.mapper.UserMapper;

/**
 * Интерфейс сервисного класса AuthServiceImpl
 *
 * @see ru.tamara.classifiedsSite.entity.User
 * @see ru.tamara.classifiedsSite.service.impl.AuthServiceImpl
 */
public interface AuthService {
    /**
     * Метод производит авторизацию пользователя в системе {@link UserServiceImpl#findAuthUser()}
     *
     * @param userName
     * @param password
     * @return {@link PasswordEncoder#matches(CharSequence, String)}
     */
    boolean login(String userName, String password);

    /**
     * Метод регистрирует пользователя в системе:
     * {@link UserMapper#mapToUser(UserDto)}, {@link PasswordEncoder#encode(CharSequence)}
     *
     * @param registerReqDto
     * @param role
     * @return {@link UserRepository#save(Object)}
     * @see UserMapper
     */
    boolean register(RegisterReqDto registerReqDto, Role role);

    /**
     * Метод меняет пароль {@link PasswordEncoder#encode(CharSequence)}
     *
     * @param newPasswordDto
     * @throws UserNotFoundException     если пользователь не найден
     * @throws UserUnauthorizedException если пользователь не аутентифицирован и поэтому не имеет права доступа к ресурсу
     */
    void changePassword(NewPasswordDto newPasswordDto);
}

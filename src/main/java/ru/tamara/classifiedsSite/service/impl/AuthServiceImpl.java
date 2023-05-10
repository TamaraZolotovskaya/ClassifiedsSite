package ru.tamara.classifiedsSite.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tamara.classifiedsSite.dto.NewPasswordDto;
import ru.tamara.classifiedsSite.dto.RegisterReqDto;
import ru.tamara.classifiedsSite.dto.Role;
import ru.tamara.classifiedsSite.dto.UserDto;
import ru.tamara.classifiedsSite.entity.User;
import ru.tamara.classifiedsSite.exception.UserNotFoundException;
import ru.tamara.classifiedsSite.exception.UserUnauthorizedException;
import ru.tamara.classifiedsSite.repository.UserRepository;
import ru.tamara.classifiedsSite.service.AuthService;
import ru.tamara.classifiedsSite.service.mapper.UserMapper;

/**
 * Класс - сервис, содержащий реализацию интерфейса {@link AuthService}
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl manager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Метод производит авторизацию пользователя в системе {@link UserServiceImpl#findAuthUser()}
     * @param userName
     * @param password
     * @return {@link PasswordEncoder#matches(CharSequence, String)}
     */
    @Override
    public boolean login(String userName, String password) {
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        return encoder.matches(password, encryptedPassword);
    }

    /**
     * Метод регистрирует пользователя в системе:
     * {@link UserMapper#mapToUser(UserDto)}, {@link PasswordEncoder#encode(CharSequence)}
     * @param registerReqDto
     * @param role
     * @return {@link UserRepository#save(Object)}
     * @see UserMapper
     */
    @Override
    public boolean register(RegisterReqDto registerReqDto, Role role) {
        if (userRepository.findByEmail(registerReqDto.getUsername()).isPresent()) {
            return false;
        }
        User regUser = userMapper.mapToUser(registerReqDto);
        regUser.setRole(role);
        regUser.setPassword(encoder.encode(regUser.getPassword()));
        userRepository.save(regUser);
        return true;
    }

    /**
     * Метод меняет пароль {@link PasswordEncoder#encode(CharSequence)}
     * @param newPasswordDto
     * @throws UserNotFoundException если пользователь не найден
     * @throws UserUnauthorizedException если пользователь не аутентифицирован и поэтому не имеет права доступа к ресурсу
     */
    @Override
    public void changePassword(NewPasswordDto newPasswordDto) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String currentEmail = principal.getUsername();
        User user = userRepository.findByEmail(currentEmail).orElseThrow(UserNotFoundException::new);
        UserDetails userDetails = manager.loadUserByUsername(user.getUsername());
        String encryptedPassword = userDetails.getPassword();
        if (encoder.matches(newPasswordDto.getCurrentPassword(), encryptedPassword)) {
            user.setPassword(encoder.encode(newPasswordDto.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new UserUnauthorizedException();
        }
    }
}

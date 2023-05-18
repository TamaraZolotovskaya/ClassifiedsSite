package ru.tamara.classifiedsSite.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.tamara.classifiedsSite.dto.NewPasswordDto;
import ru.tamara.classifiedsSite.dto.RegisterReqDto;
import ru.tamara.classifiedsSite.dto.Role;
import ru.tamara.classifiedsSite.exception.UserUnauthorizedException;
import ru.tamara.classifiedsSite.repository.UserRepository;
import ru.tamara.classifiedsSite.service.mapper.UserMapper;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.tamara.classifiedsSite.TestConstants.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    private final Role role = Role.USER;
    private final ru.tamara.classifiedsSite.entity.User user = new ru.tamara.classifiedsSite.entity.User();
    private final RegisterReqDto registerReqDto = new RegisterReqDto();
    private final NewPasswordDto newPasswordDto = new NewPasswordDto();

    @BeforeEach
    public void setUp() {
        user.setUsername(USERNAME);
        user.setPassword(ENCRYPTED_PASSWORD);

        registerReqDto.setUsername(USERNAME);
        registerReqDto.setPassword(PASSWORD);

        newPasswordDto.setCurrentPassword(PASSWORD);
        newPasswordDto.setNewPassword(DIF_PASSWORD);
    }

    @Test
    @DisplayName("Проверка авторизации, когда учетные данные действительны")
    public void loginValidCredentialsTrueTest() {
        when(userService.loadUserByUsername(USERNAME))
                .thenReturn(new org.springframework.security.core.userdetails.User(
                USERNAME, ENCRYPTED_PASSWORD, new ArrayList<>()));
        when(encoder.matches(PASSWORD, ENCRYPTED_PASSWORD)).thenReturn(true);

        boolean result = authService.login(USERNAME, PASSWORD);

        assertTrue(result);

        verify(userService, times(1)).loadUserByUsername(USERNAME);
        verify(encoder, times(1)).matches(PASSWORD, ENCRYPTED_PASSWORD);
    }

    @Test
    @DisplayName("Проверка авторизации, когда учетные данные не действительны")
    public void loginInvalidCredentialsFalseTest() {
        when(userService.loadUserByUsername(USERNAME))
                .thenReturn(new org.springframework.security.core.userdetails.User(
                        USERNAME, ENCRYPTED_PASSWORD, new ArrayList<>()));
        when(encoder.matches(PASSWORD, ENCRYPTED_PASSWORD)).thenReturn(false);

        boolean result = authService.login(USERNAME, PASSWORD);

        assertFalse(result);

        verify(userService, times(1)).loadUserByUsername(USERNAME);
        verify(encoder, times(1)).matches(PASSWORD, ENCRYPTED_PASSWORD);
    }

    @Test
    @DisplayName("Проверка успешности регистрации")
    void registerSuccessfulTest() {
        user.setPassword(PASSWORD);
        user.setRole(role);

        when(userRepository.findByEmail(USERNAME)).thenReturn(Optional.empty());
        when(userMapper.mapToUser(registerReqDto)).thenReturn(user);
        when(encoder.encode(PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);

        assertTrue(authService.register(registerReqDto, role));

        verify(userRepository, times(1)).findByEmail(USERNAME);
        verify(userMapper, times(1)).mapToUser(registerReqDto);
        verify(encoder, times(1)).encode(PASSWORD);
    }

    @Test
    @DisplayName("Проверка невозможности регистрации, когда пользователь уже зарегистрирован")
    void registerUserAlreadyExistsTest() {
        ru.tamara.classifiedsSite.entity.User existingUser = new ru.tamara.classifiedsSite.entity.User();
        existingUser.setUsername(USERNAME);

        when(userRepository.findByEmail(USERNAME)).thenReturn(Optional.of(existingUser));

        assertFalse(authService.register(registerReqDto, role));

        verify(userRepository, times(1)).findByEmail(USERNAME);
    }

    @Test
    @DisplayName("Проверка смены пароля, когда пароль из базы данных совпадает с паролем из токена")
    void changePasswordTest() {
        UserDetails userDetails = new User(USERNAME, PASSWORD, new ArrayList<>());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        String name = userDetails.getUsername();
        String pas = userDetails.getPassword();

        ru.tamara.classifiedsSite.entity.User user = new ru.tamara.classifiedsSite.entity.User();
        user.setUsername(USERNAME);

        when(userRepository.findByEmail(user.getUsername())).thenReturn(Optional.of(user));
        when(userService.loadUserByUsername(name)).thenReturn(userDetails);

        when(encoder.matches(PASSWORD, pas)).thenReturn(true);

        authService.changePassword(newPasswordDto);

        verify(userRepository, times(1)).findByEmail(user.getUsername());
        verify(userService, times(1)).loadUserByUsername(name);
        verify(encoder, times(1)).matches(PASSWORD, pas);
    }

    @Test
    @DisplayName("Проверка невозможности смены пароля, когда пароль из базы данных не совпадает с паролем из токена")
    void changePasswordWhenPasswordsNotEqualsTest() {
        UserDetails userDetails = new User(USERNAME, PASSWORD, new ArrayList<>());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        String name = userDetails.getUsername();
        String pas = userDetails.getPassword();

        ru.tamara.classifiedsSite.entity.User user = new ru.tamara.classifiedsSite.entity.User();
        user.setUsername(USERNAME);

        when(userRepository.findByEmail(user.getUsername())).thenReturn(Optional.of(user));
        when(userService.loadUserByUsername(name)).thenReturn(userDetails);

        when(encoder.matches(PASSWORD, pas)).thenReturn(false);

        assertThrows(UserUnauthorizedException.class, () -> authService.changePassword(newPasswordDto));

        verify(userRepository, times(1)).findByEmail(user.getUsername());
        verify(userService, times(1)).loadUserByUsername(name);
        verify(encoder, times(1)).matches(PASSWORD, pas);
    }
}

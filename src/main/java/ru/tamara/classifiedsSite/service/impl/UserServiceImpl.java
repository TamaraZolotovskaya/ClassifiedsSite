package ru.tamara.classifiedsSite.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tamara.classifiedsSite.dto.UserDto;
import ru.tamara.classifiedsSite.entity.Image;
import ru.tamara.classifiedsSite.entity.User;
import ru.tamara.classifiedsSite.exception.UserNotFoundException;
import ru.tamara.classifiedsSite.repository.ImageRepository;
import ru.tamara.classifiedsSite.repository.UserRepository;
import ru.tamara.classifiedsSite.service.ImageService;
import ru.tamara.classifiedsSite.service.UserService;
import ru.tamara.classifiedsSite.service.mapper.UserMapper;

import java.util.Optional;

/**
 * Класс - сервис, содержащий реализацию интерфейса {@link UserService} и {@link UserDetailsService}
 *
 * @see User
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserMapper userMapper;
    private final ImageService imageService;
    private final UserRepository userRepository;

    /**
     * Метод находит пользователя по email и возвращает его данные: имя пользователя и пароль
     *
     * @param username
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException если пользователь не найден
     * @see UserDetailsService
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User with username " + username + " doesn't exists"));
    }

    /**
     * Метод ищет авторизованного пользователя
     *
     * @return {@link UserRepository#findByEmail(String)}
     */
    @Override
    public Optional<User> findAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findByEmail(currentPrincipalName);
    }

    /**
     * Метод достает пользователя из базы данных {@link UserService#findAuthUser()} и
     * конвертирует его в {@link UserDto}
     *
     * @return {@link UserMapper#mapToUser(UserDto)}
     * @see UserMapper
     */
    @Override
    public UserDto getUserDto() {
        Optional<User> currentUser = findAuthUser();
        UserDto currentUserDto = new UserDto();
        if (currentUser.isPresent()) {
            currentUserDto = userMapper.mapToUserDto(currentUser.get());
        }
        return currentUserDto;
    }

    /**
     * Метод достает пользователя из базы данных {@link UserService#findAuthUser()},
     * редактирует данные и сохраняет в базе
     *
     * @param newUserDto
     * @return {@link UserRepository#save(Object)}, {@link UserMapper#mapToUser(UserDto)}
     * @see UserMapper
     */
    @Override
    public UserDto updateUserDto(UserDto newUserDto) {
        Optional<User> currentUser = findAuthUser();
        User newCurrentUser = new User();
        if (currentUser.isPresent()) {
            newCurrentUser = currentUser.get();
            newCurrentUser.setFirstName(newUserDto.getFirstName());
            newCurrentUser.setLastName(newUserDto.getLastName());
            newCurrentUser.setPhone(newUserDto.getPhone());
            //другие поля остаются теми же
            userRepository.save(newCurrentUser);
        }
        return userMapper.mapToUserDto(newCurrentUser);
    }

    /**
     * Метод достает пользователя из базы данных {@link UserService#findAuthUser()},
     * устанавливает или обновляет его аватар, затем сохраняет изменения в базе данных:
     * {@link ImageRepository#saveAndFlush(Object)}, {@link UserRepository#save(Object)}
     *
     * @param image
     * @return
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    public Image updateUserImage(MultipartFile image) {
        User user = findAuthUser().orElseThrow(UserNotFoundException::new);
        Image oldImage = user.getImage();
        if (oldImage == null) {
            Image newImage = imageService.saveImage(image);
            user.setImage(newImage);
        } else {
            Image updatedImage = imageService.updateImage(image, oldImage);
            user.setImage(updatedImage);
        }
        userRepository.save(user);
        return user.getImage();
    }
}

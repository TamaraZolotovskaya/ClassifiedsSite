package ru.tamara.classifiedsSite.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.tamara.classifiedsSite.dto.RegisterReqDto;
import ru.tamara.classifiedsSite.dto.UserDto;
import ru.tamara.classifiedsSite.entity.User;
import ru.tamara.classifiedsSite.service.mapper.UserMapper;

import java.util.Optional;

/**
 * Класс - сервис-маппер, содержащий реализацию интерфейса {@link UserMapper}
 */
@Component
public class UserMapperImpl implements UserMapper {

    /**
     * Метод, преобразующий объект класса User в объект класса UserDto.
     *
     * @param user
     * @return UserDto
     */
    public UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        Optional.ofNullable(user.getImage()).ifPresent(image -> userDto.setImage(
                "/users/" + user.getImage().getId() + "/image"));
        return userDto;
    }

    /**
     * Метод, преобразующий объект класса UserDto в объект класса User.
     *
     * @param userDto
     * @return User
     */
    public User mapToUser(UserDto userDto) {
        User mappedUser = new User();
        mappedUser.setId(userDto.getId());
        mappedUser.setEmail(userDto.getEmail());
        mappedUser.setFirstName(userDto.getFirstName());
        mappedUser.setLastName(userDto.getLastName());
        mappedUser.setPhone(userDto.getPhone());
        mappedUser.getImage().setId(userDto.getImage());
        return mappedUser;
    }

    /**
     * Метод, преобразующий объект класса RegisterReqDto в объект класса User.
     *
     * @param registerReqDto
     * @return User
     */
    @Override
    public User mapToUser(RegisterReqDto registerReqDto) {
        User mappedUser = new User();
        mappedUser.setUsername(registerReqDto.getUsername());
        mappedUser.setPassword(registerReqDto.getPassword());
        mappedUser.setFirstName(registerReqDto.getFirstName());
        mappedUser.setLastName(registerReqDto.getLastName());
        mappedUser.setPhone(registerReqDto.getPhone());
        mappedUser.setRole(registerReqDto.getRole());
        mappedUser.setEmail(registerReqDto.getUsername());
        //в форме регистрации username и email одно и тоже
        return mappedUser;
    }
}

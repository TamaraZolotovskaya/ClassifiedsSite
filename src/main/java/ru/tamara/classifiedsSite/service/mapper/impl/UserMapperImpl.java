package ru.tamara.classifiedsSite.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.tamara.classifiedsSite.dto.RegisterReqDto;
import ru.tamara.classifiedsSite.dto.UserDto;
import ru.tamara.classifiedsSite.entity.User;
import ru.tamara.classifiedsSite.service.mapper.UserMapper;

import java.util.Optional;

@Component
public class UserMapperImpl implements UserMapper {

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

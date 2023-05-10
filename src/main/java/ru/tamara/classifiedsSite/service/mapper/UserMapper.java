package ru.tamara.classifiedsSite.service.mapper;

import ru.tamara.classifiedsSite.dto.RegisterReqDto;
import ru.tamara.classifiedsSite.dto.UserDto;
import ru.tamara.classifiedsSite.entity.User;

public interface UserMapper {
    UserDto mapToUserDto(User user);

    User mapToUser(UserDto userDto);

    User mapToUser(RegisterReqDto registerReqDto);
}

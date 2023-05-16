package ru.tamara.classifiedsSite.service.mapper;

import ru.tamara.classifiedsSite.dto.RegisterReqDto;
import ru.tamara.classifiedsSite.dto.UserDto;
import ru.tamara.classifiedsSite.entity.User;

/**
 * Интерфейс сервисного класса-маппера UserMapperImpl, для маппинга объектов RegisterReqDto, UserDto и User
 *
 * @see ru.tamara.classifiedsSite.entity.User
 * @see ru.tamara.classifiedsSite.dto.UserDto
 * @see ru.tamara.classifiedsSite.dto.RegisterReqDto
 * @see ru.tamara.classifiedsSite.service.mapper.impl.UserMapperImpl
 */
public interface UserMapper {
    /**
     * Метод, преобразующий объект класса User в объект класса UserDto.
     *
     * @param user
     * @return UserDto
     */
    UserDto mapToUserDto(User user);

    /**
     * Метод, преобразующий объект класса UserDto в объект класса User.
     *
     * @param userDto
     * @return User
     */
    User mapToUser(UserDto userDto);

    /**
     * Метод, преобразующий объект класса RegisterReqDto в объект класса User.
     *
     * @param registerReqDto
     * @return User
     */
    User mapToUser(RegisterReqDto registerReqDto);
}

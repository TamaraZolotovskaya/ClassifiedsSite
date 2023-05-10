package ru.tamara.classifiedsSite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tamara.classifiedsSite.entity.User;

import java.util.Optional;

/**
 * Интерфейс, содержащий методы для работы с базой данных пользователей
 * @see User
 * @see ru.tamara.classifiedsSite.service.UserService
 * @see ru.tamara.classifiedsSite.service.impl.UserServiceImpl
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Метод ищет и возвращает пользователя по его email
     * @param email
     * @return User
     * @see User
     */
    Optional<User> findByEmail(String email);
}

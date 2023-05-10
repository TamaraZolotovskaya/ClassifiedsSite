package ru.tamara.classifiedsSite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tamara.classifiedsSite.entity.Comment;

/**
 * Интерфейс, содержащий методы для работы с базой данных комментариев
 * @see Comment
 * @see ru.tamara.classifiedsSite.service.CommentService
 * @see ru.tamara.classifiedsSite.service.impl.CommentServiceImpl
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}

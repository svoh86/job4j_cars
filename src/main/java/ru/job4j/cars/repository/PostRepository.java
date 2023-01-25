package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для хранилища объявлений
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */

public interface PostRepository {
    Post create(Post post);

    void update(Post post);

    void delete(int postId);

    List<Post> findAllOrderById();

    Optional<Post> findById(int postId);

    List<Post> findForLastDay();

    List<Post> findWithPhoto();

    List<Post> findByCarName(String carName);
}

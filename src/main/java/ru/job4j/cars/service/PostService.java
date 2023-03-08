package ru.job4j.cars.service;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для слоя сервис объявлений
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface PostService {
    boolean add(Post post);

    boolean update(Post post);

    boolean delete(int id);

    List<Post> findAllOrderById();

    Optional<Post> findById(int id);

    List<Post> findForLastDay();
}

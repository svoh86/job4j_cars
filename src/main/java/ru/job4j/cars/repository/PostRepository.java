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
    public Post create(Post post);

    public void update(Post post);

    public void delete(int postId);

    public List<Post> findAllOrderById();

    public Optional<Post> findById(int postId);
}

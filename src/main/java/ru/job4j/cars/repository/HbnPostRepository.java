package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Хранилище объявлений
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Repository
@AllArgsConstructor
public class HbnPostRepository implements PostRepository {
    private final CrudRepository crudRepository;
    private static final String DELETE = "DELETE Post WHERE id = :fId";
    private static final String FIND_ALL_ORDER_BY_ID = "FROM Post p JOIN FETCH p.priceHistory"
                                                       + " JOIN FETCH p.participates ORDER BY id";
    private static final String FIND_BY_ID = "FROM Post p JOIN FETCH p.priceHistory"
                                             + " JOIN FETCH p.participates WHERE id = :fId";

    /**
     * Сохранить в базе.
     *
     * @param post объявление.
     * @return объявление с id.
     */
    @Override
    public Post create(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    /**
     * Обновить в базе объявление.
     *
     * @param post объявление.
     */
    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    /**
     * Удалить объявление по id.
     *
     * @param postId ID
     */
    @Override
    public void delete(int postId) {
        crudRepository.run(
                DELETE,
                Map.of("fId", postId)
        );
    }

    /**
     * Список объявлений, отсортированных по id.
     *
     * @return список объявлений.
     */
    @Override
    public List<Post> findAllOrderById() {
        return crudRepository.query(FIND_ALL_ORDER_BY_ID, Post.class);
    }

    /**
     * Найти объявление по ID
     *
     * @return Optional объявления.
     */
    @Override
    public Optional<Post> findById(int postId) {
        return crudRepository.optional(FIND_BY_ID,
                Post.class,
                Map.of("fId", postId));
    }
}

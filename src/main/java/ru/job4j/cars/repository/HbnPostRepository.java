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
            + " JOIN FETCH p.participates ORDER BY p.id";
    private static final String FIND_BY_ID = "FROM Post p JOIN FETCH p.priceHistory"
            + " JOIN FETCH p.participates WHERE p.id = :fId";
    private static final String FIND_FOR_LAST_DAY = "FROM Post p JOIN FETCH p.priceHistory"
            + " JOIN FETCH p.participates WHERE p.created > CURRENT_DATE - 1";

    private static final String FIND_WITH_PHOTO = "FROM Post p JOIN FETCH p.priceHistory"
            + " JOIN FETCH p.participates WHERE p.photo IS NOT NULL";
    private static final String FIND_BY_CAR_NAME = "FROM Post p JOIN FETCH p.priceHistory"
            + " JOIN FETCH p.participates WHERE p.car_id IN (SELECT id FROM Car WHERE name = :fName)";

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

    /**
     * Найти объявления за последний день.
     *
     * @return список объявлений.
     */
    @Override
    public List<Post> findForLastDay() {
        return crudRepository.query(FIND_FOR_LAST_DAY, Post.class);
    }

    /**
     * Найти объявления с фото
     *
     * @return список объявлений.
     */
    @Override
    public List<Post> findWithPhoto() {
        return crudRepository.query(FIND_WITH_PHOTO, Post.class);
    }

    @Override
    public List<Post> findByCarName(String carName) {
        return crudRepository.query(FIND_BY_CAR_NAME,
                Post.class,
                Map.of("fName", carName));
    }
}

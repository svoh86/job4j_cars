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
    private static final String DELETE = "DELETE Post p WHERE p.id = :fId";
    private static final String FIND_ALL_ORDER_BY_ID_PRICE_HISTORY = "SELECT DISTINCT p FROM Post p"
            + " JOIN FETCH p.priceHistory";
    private static final String FIND_ALL_ORDER_BY_ID_PARTICIPATES = "SELECT DISTINCT p FROM Post p"
            + " JOIN FETCH p.participates WHERE p IN :fPosts ORDER BY p.id";
    private static final String FIND_BY_ID_JOIN_PARTICIPATES = "SELECT DISTINCT p FROM Post p"
            + " JOIN FETCH p.participates WHERE p.id = :fId";
    private static final String FIND_BY_ID_JOIN_PRICE_HISTORY = "SELECT DISTINCT p FROM Post p"
            + " JOIN FETCH p.priceHistory WHERE p.id = :fId";
    private static final String FIND_FOR_LAST_DAY_PRICE_HISTORY = "SELECT DISTINCT p FROM Post p"
            + " JOIN FETCH p.priceHistory WHERE p.created > CURRENT_DATE - 1";
    private static final String FIND_FOR_LAST_DAY_PARTICIPATES = "SELECT DISTINCT p FROM Post p"
            + " JOIN FETCH p.participates WHERE p IN :fPosts";
    private static final String FIND_WITH_PHOTO_PRICE_HISTORY = "SELECT DISTINCT p FROM Post p"
            + " JOIN FETCH p.priceHistory WHERE p.photo IS NOT NULL";
    private static final String FIND_WITH_PHOTO_PARTICIPATES = "SELECT DISTINCT p FROM Post p"
            + " JOIN FETCH p.participates WHERE p IN :fPosts";
    private static final String FIND_BY_CAR_NAME_PRICE_HISTORY = "SELECT DISTINCT p FROM Post p"
            + " JOIN FETCH p.priceHistory WHERE p.car.id IN (SELECT c.id FROM Car c WHERE c.name = :fName)";
    private static final String FIND_BY_CAR_NAME_PARTICIPATES = "SELECT DISTINCT p FROM Post p"
            + " JOIN FETCH p.participates WHERE p IN :fPosts";

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
        return crudRepository.queryMultiple(
                FIND_ALL_ORDER_BY_ID_PRICE_HISTORY,
                FIND_ALL_ORDER_BY_ID_PARTICIPATES,
                Post.class,
                "fPosts");
    }

    /**
     * Найти объявление по ID
     *
     * @return Optional объявления.
     */
    @Override
    public Optional<Post> findById(int postId) {
        return crudRepository.optionalMultiple(FIND_BY_ID_JOIN_PARTICIPATES, FIND_BY_ID_JOIN_PRICE_HISTORY,
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
        return crudRepository.queryMultiple(
                FIND_FOR_LAST_DAY_PRICE_HISTORY,
                FIND_FOR_LAST_DAY_PARTICIPATES,
                Post.class,
                "fPosts");
    }

    /**
     * Найти объявления с фото
     *
     * @return список объявлений.
     */
    @Override
    public List<Post> findWithPhoto() {
        return crudRepository.queryMultiple(
                FIND_WITH_PHOTO_PRICE_HISTORY,
                FIND_WITH_PHOTO_PARTICIPATES,
                Post.class,
                "fPosts");
    }

    @Override
    public List<Post> findByCarName(String carName) {
        return crudRepository.queryMultiple(
                FIND_BY_CAR_NAME_PRICE_HISTORY,
                FIND_BY_CAR_NAME_PARTICIPATES,
                Post.class,
                Map.of("fName", carName),
                "fPosts");
    }
}

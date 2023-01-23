package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Хранилище пользователей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Repository
@AllArgsConstructor
public class HbnUserRepository implements UserRepository {
    private final CrudRepository crudRepository;
    private static final String DELETE = "DELETE User WHERE id = :fId";
    private static final String FIND_ALL_ORDER_BY_ID = "FROM User ORDER BY id";
    private static final String FIND_BY_ID = "FROM User WHERE id = :fId";
    private static final String FIND_BY_LIKE_LOGIN = "FROM User WHERE login LIKE :fKey";
    private static final String FIND_BY_LOGIN = "FROM User WHERE login = :fLogin";

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    @Override
    public User create(User user) {
        crudRepository.run(session -> session.persist(user));
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    @Override
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    @Override
    public void delete(int userId) {
        crudRepository.run(
                DELETE,
                Map.of("fId", userId)
        );
    }

    /**
     * Список пользователей, отсортированных по id.
     *
     * @return список пользователей.
     */
    @Override
    public List<User> findAllOrderById() {
        return crudRepository.query(FIND_ALL_ORDER_BY_ID, User.class);
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    @Override
    public Optional<User> findById(int userId) {
        return crudRepository.optional(FIND_BY_ID,
                User.class,
                Map.of("fId", userId));
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    @Override
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                FIND_BY_LIKE_LOGIN,
                User.class,
                Map.of("fKey", "%" + key + "%"));
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    @Override
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(
                FIND_BY_LOGIN,
                User.class,
                Map.of("fLogin", login));
    }
}

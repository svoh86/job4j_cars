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
    private static final String FIND_BY_LOGIN_AND_PASSWORD = "FROM User WHERE login = :fLogin AND password = :fPassword";

    /**
     * Сохранить в базе.
     * Если будет выброшено исключение при создании пользователя,
     * например, нарушено ограничение уникальности логина, тогда
     * метод вернет пустой Optional.
     *
     * @param user пользователь.
     * @return Optional пользователя с id или пустой Optional.
     */
    @Override
    public Optional<User> create(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.ofNullable(user);
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь
     * @return boolean
     */
    @Override
    public boolean update(User user) {
        return crudRepository.condition(session -> user.equals(session.merge(user)));
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    @Override
    public boolean delete(int userId) {
        return crudRepository.condition(
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
     * Найти пользователя по login и password.
     *
     * @param login login.
     * @return Optional or user.
     */
    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                FIND_BY_LOGIN_AND_PASSWORD,
                User.class,
                Map.of("fLogin", login,
                        "fPassword", password));
    }
}

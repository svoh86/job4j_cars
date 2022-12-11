package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Хранилище пользователей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Repository
@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;
    private static final String UPDATE = "UPDATE User SET login = :fLogin, password = :fPassword WHERE id = :fId";
    private static final String DELETE = "DELETE User WHERE id = :fId";
    private static final String FIND_ALL_ORDER_BY_ID = "from User ORDER BY id";
    private static final String FIND_BY_ID = "from User WHERE id = :fId";
    private static final String FIND_BY_LIKE_LOGIN = "from User WHERE login LIKE :fKey";
    private static final String FIND_BY_LOGIN = "from User WHERE login = :fLogin";

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public boolean update(User user) {
        Session session = sf.openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createQuery(UPDATE)
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return i != 0;
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public boolean delete(int userId) {
        Session session = sf.openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createQuery(DELETE)
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return i != 0;
    }

    /**
     * Список пользователей, отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(FIND_ALL_ORDER_BY_ID, User.class);
        List<User> userList = query.list();
        session.close();
        return userList;
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(FIND_BY_ID, User.class)
                .setParameter("fId", userId);
        Optional<User> userOptional = query.uniqueResultOptional();
        session.close();
        return userOptional;
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(FIND_BY_LIKE_LOGIN, User.class)
                .setParameter("fKey", "%" + key + "%");
        List<User> userList = query.list();
        session.close();
        return userList;
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(FIND_BY_LOGIN, User.class)
                .setParameter("fLogin", login);
        Optional<User> userOptional = query.uniqueResultOptional();
        session.close();
        return userOptional;
    }
}

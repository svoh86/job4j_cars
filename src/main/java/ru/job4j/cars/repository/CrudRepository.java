package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * В данный класс вынесены команды Hibernate,
 * чтобы ими можно было пользоваться из нескольких репозиториев.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Repository
@AllArgsConstructor
public class CrudRepository {
    /**
     * Объект конфигуратор. В нем происходит создания пулов, загрузка кешей, проверка моделей.
     */
    private final SessionFactory sf;

    /**
     * Метод принимает "команду" и передает ее на выполнение в метод tx().
     *
     * @param command команда
     */
    public void run(Consumer<Session> command) {
        tx(session -> {
            command.accept(session);
            return null;
        });
    }

    /**
     * Метод принимает запрос и Map с параметрами запроса.
     * Далее создается команда, которую передаем на выполнение в метод tx(),
     * через метод run(Consumer<Session> command).
     *
     * @param query запрос
     * @param args  Map с параметрами запроса
     */
    public void run(String query, Map<String, Object> args) {
        Consumer<Session> command = session -> {
            var sq = session.createQuery(query);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            sq.executeUpdate();
        };
        run(command);
    }

    /**
     * Метод принимает запрос, класс объекта и Map с параметрами запроса.
     * Далее создается команда, которую передаем на выполнение в метод tx().
     *
     * @param query запрос
     * @param cl    класс объекта
     * @param args  Map с параметрами запроса
     * @param <T>   общий тип
     * @return Optional
     */
    public <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.uniqueResultOptional();
        };
        return tx(command);
    }

    /**
     * Метод принимает два запроса, класс объекта и Map с параметрами запроса.
     * Далее создается команда, которую передаем на выполнение в метод tx().
     *
     * @param query  запрос
     * @param query2 запрос2
     * @param cl     класс объекта
     * @param args   Map с параметрами запроса
     * @param <T>    общий тип
     * @return Optional
     */
    public <T> Optional<T> optionalMultiple(String query, String query2, Class<T> cl, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            Optional<T> t = sq.uniqueResultOptional();
            if (t.isPresent()) {
                var sq2 = session.createQuery(query2, cl);
                for (Map.Entry<String, Object> arg : args.entrySet()) {
                    sq2.setParameter(arg.getKey(), arg.getValue());
                }
                return sq2.uniqueResultOptional();
            } else {
                return t;
            }
        };
        return tx(command);
    }

    /**
     * Метод принимает запрос и класс объекта
     * Далее создается команда, которую передаем на выполнение в метод tx().
     *
     * @param query запрос
     * @param cl    класс объекта
     * @param <T>   общий тип
     * @return список объектов типа Т
     */
    public <T> List<T> query(String query, Class<T> cl) {
        Function<Session, List<T>> command = session -> session
                .createQuery(query, cl)
                .list();
        return tx(command);
    }

    /**
     * Метод принимает два запроса, класс объекта и параметр запроса.
     * Далее создается команда, которую передаем на выполнение в метод tx().
     *
     * @param query  запрос
     * @param query2 запрос2
     * @param cl     класс объекта
     * @param arg    параметр
     * @param <T>    общий тип
     * @return список объектов типа Т
     */
    public <T> List<T> queryMultiple(String query, String query2, Class<T> cl, String arg) {
        Function<Session, List<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            List<T> list = sq.list();
            var sq2 = session.createQuery(query2, cl);
            sq2.setParameter(arg, list);
            return sq2.list();
        };
        return tx(command);
    }

    /**
     * Метод принимает два запроса, класс объекта, Map с параметрами запроса и параметр запроса.
     * Далее создается команда, которую передаем на выполнение в метод tx()
     *
     * @param query  запрос
     * @param query2 запрос2
     * @param cl     класс объекта
     * @param args   Map с параметрами запроса
     * @param s      параметр
     * @param <T>    общий тип
     * @return список объектов типа Т
     */
    public <T> List<T> queryMultiple(String query, String query2, Class<T> cl, Map<String, Object> args, String s) {
        Function<Session, List<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            List<T> list = sq.list();
            var sq2 = session.createQuery(query2, cl);
            sq2.setParameter(s, list);
            return sq2.list();
        };
        return tx(command);
    }

    /**
     * Метод принимает запрос, класс объекта и Map с параметрами запроса.
     * Далее создается команда, которую передаем на выполнение в метод tx().
     *
     * @param query запрос
     * @param cl    класс объекта
     * @param args  Map с параметрами запроса
     * @param <T>   общий тип
     * @return список объектов типа Т
     */
    public <T> List<T> query(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, List<T>> command = session -> {
            var sq = session
                    .createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.list();
        };
        return tx(command);
    }

    /**
     * Метод выполняет абстрактную операцию.
     * Он принимает некую "команду", открывает сессию, начинает транзакцию и выполняет эту команду.
     * Команда принимается в виде объекта функционального интерфейса,
     * благодаря чему достигается абстрактность операции.
     * Методу tx() не важно, придет команда на вставку данных, изменение, удаление и т.д.
     * Он не знает её внутреннюю реализацию, он просто получает команду и выполняет её.
     *
     * @param command команда
     * @param <T>     общий тип
     * @return общий тип
     */
    public <T> T tx(Function<Session, T> command) {
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}

package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Хранилище владельцев автомобилей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Repository
@AllArgsConstructor
public class HbnDriverRepository implements DriverRepository {
    private final CrudRepository crudRepository;
    private static final String DELETE = "DELETE Driver WHERE id = :fId";
    private static final String FIND_ALL_ORDER_BY_ID = "FROM Driver ORDER BY id";
    private static final String FIND_BY_ID = "FROM Driver WHERE id = :fId";

    /**
     * Сохранить в базе.
     *
     * @param driver владелец.
     * @return владелец с id.
     */
    @Override
    public Driver create(Driver driver) {
        crudRepository.run(session -> session.persist(driver));
        return driver;
    }

    /**
     * Обновить в базе владельца.
     *
     * @param driver владелец.
     */
    @Override
    public void update(Driver driver) {
        crudRepository.run(session -> session.merge(driver));
    }

    /**
     * Удалить владельца по id.
     *
     * @param driverId ID
     */
    @Override
    public void delete(int driverId) {
        crudRepository.run(
                DELETE,
                Map.of("fId", driverId)
        );
    }

    /**
     * Список владельцев, отсортированных по id.
     *
     * @return список владельцев.
     */
    @Override
    public List<Driver> findAllOrderById() {
        return crudRepository.query(FIND_ALL_ORDER_BY_ID, Driver.class);
    }

    /**
     * Найти владельца по ID
     *
     * @return Optional владельца.
     */
    @Override
    public Optional<Driver> findById(int driverId) {
        return crudRepository.optional(FIND_BY_ID,
                Driver.class,
                Map.of("fId", driverId));
    }
}

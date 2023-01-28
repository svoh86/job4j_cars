package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Хранилище автомобилей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Repository
@AllArgsConstructor
public class HbnCarRepository implements CarRepository {
    private final CrudRepository crudRepository;
    private static final String DELETE = "DELETE Car WHERE id = :fId";
    private static final String FIND_ALL_ORDER_BY_ID = "FROM Car c JOIN FETCH c.owners ORDER BY c.id";
    private static final String FIND_BY_ID = "FROM Car c JOIN FETCH c.owners WHERE c.id = :fId";

    /**
     * Сохранить в базе.
     *
     * @param car автомобиль.
     * @return автомобиль с id.
     */
    @Override
    public Car create(Car car) {
        crudRepository.run(session -> session.persist(car));
        return car;
    }

    /**
     * Обновить в базе автомобиль.
     *
     * @param car автомобиль.
     */
    @Override
    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    /**
     * Удалить автомобиль по id.
     *
     * @param carId ID
     */
    @Override
    public void delete(int carId) {
        crudRepository.run(
                DELETE,
                Map.of("fId", carId)
        );
    }

    /**
     * Список автомобилей, отсортированных по id.
     *
     * @return список автомобилей.
     */
    @Override
    public List<Car> findAllOrderById() {
        return crudRepository.query(FIND_ALL_ORDER_BY_ID, Car.class);
    }

    /**
     * Найти автомобиль по ID
     *
     * @return Optional автомобиля.
     */
    @Override
    public Optional<Car> findById(int carId) {
        return crudRepository.optional(FIND_BY_ID,
                Car.class,
                Map.of("fId", carId));
    }
}

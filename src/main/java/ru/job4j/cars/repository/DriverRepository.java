package ru.job4j.cars.repository;

import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для хранилища владельцев автомобилей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */

public interface DriverRepository {
    Driver create(Driver driver);

    boolean update(Driver driver);

    void delete(int driverId);

    List<Driver> findAllOrderById();

    Optional<Driver> findById(int driverId);
}

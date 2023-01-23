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
    public Driver create(Driver driver);

    public void update(Driver driver);

    public void delete(int driverId);

    public List<Driver> findAllOrderById();

    public Optional<Driver> findById(int driverId);
}

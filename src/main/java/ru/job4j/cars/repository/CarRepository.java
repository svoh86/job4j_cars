package ru.job4j.cars.repository;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для хранилища автомобилей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */

public interface CarRepository {
    Car create(Car car);

    boolean update(Car car);

    void delete(int carId);

    List<Car> findAllOrderById();

    Optional<Car> findById(int carId);
}

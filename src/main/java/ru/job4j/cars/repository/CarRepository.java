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
    public Car create(Car car);

    public void update(Car car);

    public void delete(int carId);

    public List<Car> findAllOrderById();

    public Optional<Car> findById(int carId);
}

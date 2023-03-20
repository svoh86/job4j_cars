package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;

import java.util.Optional;

/**
 * Интерфейс для слоя сервис авто
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface CarService {
    Car add(Car car);

    boolean update(Car car);

    void delete(int id);

    Optional<Car> findById(int id);
}

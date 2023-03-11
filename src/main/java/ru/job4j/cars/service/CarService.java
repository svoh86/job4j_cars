package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.Engine;

import java.util.Optional;

/**
 * Интерфейс для слоя сервис авто
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface CarService {
    Car add(String carName, Engine engine, Driver driver);

    boolean update(Car car);

    void delete(int id);

    Optional<Car> findById(int id);
}

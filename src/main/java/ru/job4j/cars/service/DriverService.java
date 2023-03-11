package ru.job4j.cars.service;

import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;

import java.util.Optional;

/**
 * Интерфейс для слоя сервис владельцев авто
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface DriverService {
    Driver add(String driverName, User user);

    boolean update(Driver driver);

    void delete(int id);

    Optional<Driver> findById(int id);
}

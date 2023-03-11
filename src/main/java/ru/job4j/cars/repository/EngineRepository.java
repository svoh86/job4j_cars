package ru.job4j.cars.repository;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для хранилища двигателей автомобилей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */

public interface EngineRepository {
    Engine create(Engine engine);

    boolean update(Engine engine);

    void delete(int engineId);

    List<Engine> findAllOrderById();

    Optional<Engine> findById(int engineId);
}

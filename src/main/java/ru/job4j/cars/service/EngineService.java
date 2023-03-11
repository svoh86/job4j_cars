package ru.job4j.cars.service;

import ru.job4j.cars.model.Engine;

import java.util.Optional;

/**
 * Интерфейс для слоя сервис двигателей авто
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface EngineService {
    Engine add(String engineName);

    boolean update(Engine engine);

    void delete(int id);

    Optional<Engine> findById(int id);
}

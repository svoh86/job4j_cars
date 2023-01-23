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
    public Engine create(Engine engine);

    public void update(Engine engine);

    public void delete(int engineId);

    public List<Engine> findAllOrderById();

    public Optional<Engine> findById(int engineId);
}

package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Хранилище двигателей автомобилей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Repository
@AllArgsConstructor
public class HbnEngineRepository implements EngineRepository {
    private final CrudRepository crudRepository;
    private static final String DELETE = "DELETE Engine WHERE id = :fId";
    private static final String FIND_ALL_ORDER_BY_ID = "FROM Engine ORDER BY id";
    private static final String FIND_BY_ID = "FROM Engine WHERE id = :fId";

    /**
     * Сохранить в базе.
     *
     * @param engine владелец.
     * @return владелец с id.
     */
    @Override
    public Engine create(Engine engine) {
        crudRepository.run(session -> session.persist(engine));
        return engine;
    }

    /**
     * Обновить в базе двигатель.
     *
     * @param engine двигатель.
     */
    @Override
    public void update(Engine engine) {
        crudRepository.run(session -> session.merge(engine));
    }

    /**
     * Удалить двигатель по id.
     *
     * @param engineId ID
     */
    @Override
    public void delete(int engineId) {
        crudRepository.run(
                DELETE,
                Map.of("fId", engineId)
        );
    }

    /**
     * Список двигателей, отсортированных по id.
     *
     * @return список двигателей.
     */
    @Override
    public List<Engine> findAllOrderById() {
        return crudRepository.query(FIND_ALL_ORDER_BY_ID, Engine.class);
    }

    /**
     * Найти двигатель по ID
     *
     * @return Optional двигателя.
     */
    @Override
    public Optional<Engine> findById(int engineId) {
        return crudRepository.optional(FIND_BY_ID,
                Engine.class,
                Map.of("fId", engineId));
    }
}

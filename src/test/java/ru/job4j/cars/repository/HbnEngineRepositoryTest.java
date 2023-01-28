package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HbnEngineRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final HbnEngineRepository repository = new HbnEngineRepository(crudRepository);

    @Test
    public void whenAddThenFindById() {
        Engine engine = new Engine();
        engine.setName("petrol");
        repository.create(engine);
        assertThat(engine).isEqualTo(repository.findById(engine.getId()).get());
    }

    @Test
    public void whenFindAllOrderById() {
        Engine engine = new Engine();
        engine.setName("petrol");
        Engine engine2 = new Engine();
        engine2.setName("diesel");
        repository.create(engine);
        repository.create(engine2);
        List<Engine> engineList = List.of(engine, engine2);
        assertThat(repository.findAllOrderById()).isEqualTo(engineList);
    }

    @Test
    public void whenUpdate() {
        Engine engine = new Engine();
        engine.setName("petrol");
        repository.create(engine);
        engine.setName("diesel");
        repository.update(engine);
        assertThat(repository.findById(engine.getId()).get()).isEqualTo(engine);
    }

    @Test
    public void whenDelete() {
        Engine engine = new Engine();
        engine.setName("petrol");
        repository.create(engine);
        repository.delete(engine.getId());
        assertThat(repository.findById(engine.getId()).isEmpty()).isTrue();
    }
}
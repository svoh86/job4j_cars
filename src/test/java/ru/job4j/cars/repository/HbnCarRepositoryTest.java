package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class HbnCarRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final HbnCarRepository carRepository = new HbnCarRepository(crudRepository);
    private final HbnEngineRepository engineRepository = new HbnEngineRepository(crudRepository);
    private final HbnDriverRepository driverRepository = new HbnDriverRepository(crudRepository);
    private final HbnUserRepository userRepository = new HbnUserRepository(crudRepository);
    private final Engine engine = new Engine();
    private final Driver driver = new Driver();
    private final Car car = new Car();

    @BeforeEach
    private void beforeEach() {
        engine.setName("petrol");
        engineRepository.create(engine);
        User user = new User("login", "password");
        userRepository.create(user);
        driver.setName("Ivan");
        driver.setUser(user);
        driverRepository.create(driver);
        car.setName("BMW");
        car.setDriver(driver);
        car.setEngine(engine);
        car.setOwners(Set.of(driver));
        carRepository.create(car);
    }

    @Test
    public void whenAddThenFindById() {
        assertThat(car).isEqualTo(carRepository.findById(car.getId()).get());
    }

    @Test
    public void whenFindAllOrderById() {
        Car car2 = new Car();
        car2.setName("BMW");
        car2.setDriver(driver);
        car2.setEngine(engine);
        car2.setOwners(Set.of(driver));
        carRepository.create(car2);
        List<Car> carList = List.of(car, car2);
        assertThat(carRepository.findAllOrderById()).isEqualTo(carList);
    }

    @Test
    public void whenUpdate() {
        car.setName("AUDI");
        carRepository.update(car);
        assertThat(carRepository.findById(car.getId()).get()).isEqualTo(car);
    }

    @Test
    public void whenDelete() {
        carRepository.delete(car.getId());
        assertThat(carRepository.findById(car.getId()).isEmpty()).isTrue();
    }
}
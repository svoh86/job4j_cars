package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HbnDriverRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final HbnDriverRepository driverRepository = new HbnDriverRepository(crudRepository);
    private final HbnUserRepository userRepository = new HbnUserRepository(crudRepository);
    private final Driver driver = new Driver();

    @BeforeEach
    private void beforeEach() {
        User user = new User("login", "password");
        userRepository.create(user);
        driver.setName("Ivan");
        driver.setUser(user);
        driverRepository.create(driver);
    }

    @Test
    public void whenAddThenFindById() {
        assertThat(driver).isEqualTo(driverRepository.findById(driver.getId()).get());
    }

    @Test
    public void whenFindAllOrderById() {
        Driver driver2 = new Driver();
        driver2.setName("Petr");
        driverRepository.create(driver2);
        List<Driver> driverList = List.of(driver, driver2);
        assertThat(driverRepository.findAllOrderById()).isEqualTo(driverList);
    }

    @Test
    public void whenUpdate() {
        driver.setName("Petr");
        driverRepository.update(driver);
        assertThat(driverRepository.findById(driver.getId()).get()).isEqualTo(driver);
    }

    @Test
    public void whenDelete() {
        driverRepository.delete(driver.getId());
        assertThat(driverRepository.findById(driver.getId()).isEmpty()).isTrue();
    }
}
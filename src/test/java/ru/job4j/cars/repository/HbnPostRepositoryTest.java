package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class HbnPostRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final HbnPostRepository postRepository = new HbnPostRepository(crudRepository);
    private final HbnUserRepository userRepository = new HbnUserRepository(crudRepository);
    private final HbnCarRepository carRepository = new HbnCarRepository(crudRepository);
    private final HbnEngineRepository engineRepository = new HbnEngineRepository(crudRepository);
    private final HbnDriverRepository driverRepository = new HbnDriverRepository(crudRepository);
    private final User user = new User("login", "password");
    private final Car car = new Car();
    private final Engine engine = new Engine();
    private final Driver driver = new Driver();


    @BeforeEach
    private void beforeEach() {
        engine.setName("petrol");
        engineRepository.create(engine);
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
        Post post = new Post("post");
        post.setUser(user);
        post.setCar(car);
        post.setParticipates(List.of(user));

        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(100L);
        priceHistory.setAfter(200L);

        post.setPriceHistory(List.of(priceHistory));
        postRepository.create(post);
        Optional<Post> byId = postRepository.findById(post.getId());
        assertThat(post).isEqualTo(byId.get());
    }

    @Test
    public void whenFindAllOrderById() {
        Post post = new Post("post");
        post.setUser(user);
        post.setCar(car);
        post.setParticipates(List.of(user));
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(100L);
        priceHistory.setAfter(200L);
        post.setPriceHistory(List.of(priceHistory));
        postRepository.create(post);

        Post post2 = new Post();
        post2.setUser(user);
        post2.setCar(car);
        post2.setParticipates(List.of(user));
        post2.setPriceHistory(List.of(priceHistory));
        postRepository.create(post2);
        List<Post> postList = List.of(post, post2);
        assertThat(postRepository.findAllOrderById()).isEqualTo(postList);
    }
}
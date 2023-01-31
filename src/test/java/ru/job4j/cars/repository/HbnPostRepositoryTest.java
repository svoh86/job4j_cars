package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.List;
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
        Post byId = postRepository.findById(post.getId()).get();
        assertThat(post).isEqualTo(byId);
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
        PriceHistory priceHistory2 = new PriceHistory();
        priceHistory2.setBefore(300L);
        priceHistory2.setAfter(400L);
        post2.setPriceHistory(List.of(priceHistory2));
        postRepository.create(post2);
        List<Post> postList = List.of(post, post2);
        assertThat(postRepository.findAllOrderById()).isEqualTo(postList);
    }

    @Test
    public void whenUpdate() {
        Post post = new Post("New post");
        post.setUser(user);
        post.setCar(car);
        post.setParticipates(List.of(user));
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(100L);
        priceHistory.setAfter(200L);
        post.setPriceHistory(List.of(priceHistory));
        postRepository.create(post);
        post.setText("Update");
        postRepository.update(post);
        assertThat(postRepository.findById(post.getId()).get().getText()).isEqualTo("Update");
    }

    @Test
    public void whenDelete() {
        Post post = new Post("New post");
        post.setUser(user);
        post.setCar(car);
        post.setParticipates(List.of(user));
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(100L);
        priceHistory.setAfter(200L);
        post.setPriceHistory(List.of(priceHistory));
        postRepository.create(post);
        postRepository.delete(post.getId());
        assertThat(postRepository.findById(post.getId()).isEmpty()).isTrue();
    }

    @Test
    public void whenFindForLastDay() {
        Post post = new Post("post");
        post.setUser(user);
        post.setCar(car);
        post.setCreated(LocalDateTime.now().minusDays(5));
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
        PriceHistory priceHistory2 = new PriceHistory();
        priceHistory2.setBefore(300L);
        priceHistory2.setAfter(400L);
        post2.setPriceHistory(List.of(priceHistory2));
        postRepository.create(post2);
        List<Post> postList = List.of(post2);
        assertThat(postRepository.findForLastDay()).isEqualTo(postList);
    }

    @Test
    public void whenFindWithPhoto() {
        Post post = new Post("post");
        post.setUser(user);
        post.setCar(car);
        post.setPhoto(new byte[1]);
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
        PriceHistory priceHistory2 = new PriceHistory();
        priceHistory2.setBefore(300L);
        priceHistory2.setAfter(400L);
        post2.setPriceHistory(List.of(priceHistory2));
        postRepository.create(post2);
        List<Post> postList = List.of(post);
        assertThat(postRepository.findWithPhoto()).isEqualTo(postList);
    }

    @Test
    public void whenFindByCarName() {
        Post post = new Post("post");
        post.setUser(user);
        post.setCar(car);
        post.setPhoto(new byte[1]);
        post.setParticipates(List.of(user));
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(100L);
        priceHistory.setAfter(200L);
        post.setPriceHistory(List.of(priceHistory));
        postRepository.create(post);

        Car car2 = new Car();
        car2.setName("AUDI");
        car2.setDriver(driver);
        car2.setEngine(engine);
        car2.setOwners(Set.of(driver));
        carRepository.create(car2);

        Post post2 = new Post();
        post2.setUser(user);
        post2.setCar(car2);
        post2.setParticipates(List.of(user));
        PriceHistory priceHistory2 = new PriceHistory();
        priceHistory2.setBefore(300L);
        priceHistory2.setAfter(400L);
        post2.setPriceHistory(List.of(priceHistory2));
        postRepository.create(post2);
        List<Post> postList = List.of(post2);
        assertThat(postRepository.findByCarName("AUDI")).isEqualTo(postList);
    }


}
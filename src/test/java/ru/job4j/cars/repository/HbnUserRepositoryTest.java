package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HbnUserRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final HbnUserRepository repository = new HbnUserRepository(crudRepository);

    @Test
    public void whenAddThenFindById() {
        User user = new User("login", "password");
        repository.create(user);
        assertThat(user).isEqualTo(repository.findById(user.getId()).get());
    }

    @Test
    public void whenFindAllOrderById() {
        User user = new User("login", "password");
        User user2 = new User("login2", "password2");
        repository.create(user);
        repository.create(user2);
        List<User> userList = List.of(user, user2);
        assertThat(repository.findAllOrderById()).isEqualTo(userList);
    }

    @Test
    public void whenUpdate() {
        User user = new User("login", "password");
        repository.create(user);
        user.setLogin("admin");
        repository.update(user);
        assertThat(repository.findById(user.getId()).get()).isEqualTo(user);
    }

    @Test
    public void whenDelete() {
        User user = new User("login", "password");
        repository.create(user);
        repository.delete(user.getId());
        assertThat(repository.findById(user.getId()).isEmpty()).isTrue();
    }

    @Test
    public void whenFindByLogin() {
        User user = new User("login", "password");
        repository.create(user);
        assertThat(repository.findByLoginAndPassword("login", "password").get()).isEqualTo(user);
    }

    @Test
    public void whenFindByLikeLogin() {
        User user = new User("login", "password");
        User user2 = new User("login2", "password2");
        repository.create(user);
        repository.create(user2);
        List<User> userList = List.of(user, user2);
        assertThat(repository.findByLikeLogin("ogi")).isEqualTo(userList);
    }
}
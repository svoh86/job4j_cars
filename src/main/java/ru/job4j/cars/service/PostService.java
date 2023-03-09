package ru.job4j.cars.service;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для слоя сервис объявлений
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface PostService {
    boolean add(Post post, User user, Car car, PriceHistory priceHistory, MultipartFile file);

    boolean update(Post post);

    boolean delete(int id);

    List<Post> findAllOrderById();

    Optional<Post> findById(int id);

    List<Post> findForLastDay();
}

package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.PostRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class SimplePostService implements PostService {
    private final PostRepository postRepository;

    @Override
    public boolean add(Post post, User user, Car car,
                       PriceHistory priceHistory, MultipartFile file) {
        post.setCreated(LocalDateTime.now());
        post.setUser(user);
        post.setCar(car);
        post.setPriceHistory(List.of(priceHistory));
        post.setParticipates(List.of(user));
        try {
            post.setPhoto(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке фото", e);
        }
        return postRepository.create(post) != null;
    }

    @Override
    public boolean update(Post post, Car car, MultipartFile file) {
        post.setCar(car);
        try {
            post.setPhoto(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке фото", e);
        }
        return postRepository.update(post);
    }

    @Override
    public boolean delete(Post post) {
        postRepository.delete(post);
        Optional<Post> optionalPost = postRepository.findById(post.getId());
        return optionalPost.isEmpty();
    }

    @Override
    public List<Post> findAllOrderById() {
        return postRepository.findAllOrderById();
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findForLastDay() {
        return postRepository.findForLastDay();
    }

    @Override
    public List<Post> findByUserId(int userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public boolean isSale(int postId) {
        return postRepository.isSale(postId);
    }

    @Override
    public List<Post> findWithPhoto() {
        return postRepository.findWithPhoto();
    }
}

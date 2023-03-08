package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class SimplePostServise implements PostService {
    private final PostRepository postRepository;

    @Override
    public boolean add(Post post) {
        return postRepository.create(post) != null;
    }

    @Override
    public boolean update(Post post) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Post> findAllOrderById() {
        return postRepository.findAllOrderById();
    }

    @Override
    public Optional<Post> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Post> findForLastDay() {
        return null;
    }
}

package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.util.UserSession;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер объявлений
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public String posts(Model model, HttpSession session) {
        UserSession.getUser(model, session);
        model.addAttribute("posts", postService.findAllOrderById());
        return "posts/posts";
    }
}

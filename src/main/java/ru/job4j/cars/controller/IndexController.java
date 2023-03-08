package ru.job4j.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Для перехода с индексной страницы на posts
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Controller
public class IndexController {
    @GetMapping("/index")
    public String index() {
        return "redirect:/posts";
    }
}

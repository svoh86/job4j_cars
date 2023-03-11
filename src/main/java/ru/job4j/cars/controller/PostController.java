package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.CarService;
import ru.job4j.cars.service.DriverService;
import ru.job4j.cars.service.EngineService;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.util.UserSession;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

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
    private final CarService carService;
    private final DriverService driverService;
    private final EngineService engineService;

    @GetMapping
    public String posts(Model model, HttpSession session) {
        UserSession.getUser(model, session);
        model.addAttribute("posts", postService.findAllOrderById());
        return "posts/posts";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session) {
        UserSession.getUser(model, session);
        return "posts/add";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Post post,
                         @RequestParam(value = "car.name", required = false) String carName,
                         @RequestParam(value = "engine.name", required = false) String engineName,
                         @RequestParam(value = "driver.name", required = false) String driverName,
                         @RequestParam("file") MultipartFile file,
                         Model model, HttpSession session) {
        User user = UserSession.getUser(model, session);
        Driver driver = driverService.add(driverName, user);
        Engine engine = engineService.add(engineName);
        Car car = carService.add(carName, engine, driver);
        PriceHistory priceHistory = new PriceHistory(post.getPrice(), post.getPrice());
        if (!postService.add(post, user, car, priceHistory, file)) {
            model.addAttribute("message", "Объявление не создано!");
            return "errorPage";
        }
        return "redirect:/posts";
    }

    @GetMapping("/{postId}")
    public String viewPost(Model model, HttpSession session, @PathVariable("postId") Integer postId) {
        UserSession.getUser(model, session);
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление не существует!");
            return "errorPage";
        }
        model.addAttribute("post", optionalPost.get());
        return "posts/view";
    }

    @GetMapping("/myPosts")
    public String myPosts(Model model, HttpSession session) {
        User user = UserSession.getUser(model, session);
        model.addAttribute("posts", postService.findByUserId(user.getId()));
        return "posts/myPosts";
    }

    @GetMapping("delete/{postId}")
    public String deletePost(Model model, HttpSession session, @PathVariable("postId") Integer postId) {
        UserSession.getUser(model, session);
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление не существует!");
            return "errorPage";
        }
        postService.delete(optionalPost.get());
        return "redirect:/posts/myPosts";
    }

    @GetMapping("edit/{postId}")
    public String editPost(Model model, HttpSession session, @PathVariable("postId") Integer postId) {
        UserSession.getUser(model, session);
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление не существует!");
            return "errorPage";
        }
        model.addAttribute("post", optionalPost.get());
        return "posts/edit";
    }

    @GetMapping("isSale/{postId}")
    public String isSale(Model model, HttpSession session, @PathVariable("postId") Integer postId) {
        UserSession.getUser(model, session);
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление не существует!");
            return "errorPage";
        }
        postService.isSale(postId);
        return "posts/myPosts";
    }

    @GetMapping("update/{postId}")
    public String updatePost(Model model, HttpSession session, @PathVariable("postId") Integer postId) {
        UserSession.getUser(model, session);
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление не существует!");
            return "errorPage";
        }
        model.addAttribute("post", optionalPost.get());
        session.setAttribute("post", optionalPost.get());
        return "posts/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Post post,
                         @RequestParam(value = "car.name", required = false) String carName,
                         @RequestParam("file") MultipartFile file,
                         Model model, HttpSession session) {
        User user = UserSession.getUser(model, session);
        Post postSession = (Post) session.getAttribute("post");
        Driver driver = postSession.getCar().getDriver();
        driver.setName(post.getCar().getDriver().getName());
        driverService.update(driver);
        Engine engine = postSession.getCar().getEngine();
        engine.setName(post.getCar().getEngine().getName());
        engineService.update(engine);
        Car car = postSession.getCar();
        car.setDriver(driver);
        car.setEngine(engine);
        car.setName(carName);
        carService.update(car);
        List<PriceHistory> priceHistory = postSession.getPriceHistory();
        priceHistory.add(new PriceHistory(postSession.getPrice(), post.getPrice()));
        post.setId(postSession.getId());
        post.setUser(user);
        post.setPriceHistory(priceHistory);
        post.setParticipates(postSession.getParticipates());
        if (!postService.update(post, car, file)) {
            model.addAttribute("message", "Объявление не обновлено!");
            return "errorPage";
        }
        return "redirect:/posts/myPosts";
    }


    /**
     * Метод, который будет возвращать файлы
     *
     * @param postId id объявления
     * @return Если объявление не найдено по id, то клиенту возвращается статус 404,
     * а если найден, то статус 200 с телом ответа в виде содержимого файла.
     */
    @GetMapping("/photoCar/{postId}")
    public ResponseEntity<Resource> download(@PathVariable("postId") Integer postId) {
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(optionalPost.get().getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(optionalPost.get().getPhoto()));
    }
}

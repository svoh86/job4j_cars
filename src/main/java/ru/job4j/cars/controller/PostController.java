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

    /**
     * Показывает основную страницу со всеми объявлениями
     *
     * @param model   Model
     * @return posts/posts
     */
    @GetMapping
    public String posts(Model model) {
        model.addAttribute("posts", postService.findAllOrderById());
        return "posts/posts";
    }

    /**
     * Показывает страницу с формой добавления объявления
     *
     * @return posts/add
     */
    @GetMapping("/add")
    public String add() {
        return "posts/add";
    }

    /**
     * Добавляет данные из формы в БД
     *
     * @param post       объявление
     * @param carName    марка авто
     * @param engineName объем двигателя
     * @param driverName имя владельца
     * @param file       MultipartFile
     * @param model      Model
     * @param session    HttpSession
     * @return redirect:/posts или errorPage
     */
    @PostMapping("/create")
    public String create(@ModelAttribute Post post,
                         @RequestParam(value = "car.name", required = false) String carName,
                         @RequestParam(value = "engine.name", required = false) String engineName,
                         @RequestParam(value = "driver.name", required = false) String driverName,
                         @RequestParam("file") MultipartFile file,
                         Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Driver driver = driverService.add(driverName, user);
        Engine engine = engineService.add(engineName);
        Car car = new Car();
        car.setName(carName);
        car.setEngine(engine);
        car.setDriver(driver);
        carService.add(car);
        PriceHistory priceHistory = new PriceHistory(post.getPrice(), post.getPrice());
        if (!postService.add(post, user, car, priceHistory, file)) {
            model.addAttribute("message", "Объявление не создано!");
            return "errorPage";
        }
        return "redirect:/posts";
    }

    /**
     * Показывает страницу с конкретным объявлением
     *
     * @param model   Model
     * @param postId  id объявления
     * @return posts/view или errorPage
     */
    @GetMapping("/{postId}")
    public String viewPost(Model model, @PathVariable("postId") Integer postId) {
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление не существует!");
            return "errorPage";
        }
        model.addAttribute("post", optionalPost.get());
        model.addAttribute("priceHistory", optionalPost.get().getPriceHistory());
        return "posts/view";
    }

    /**
     * Показывает страницу с объявлениями конкретного пользователя
     *
     * @param model   Model
     * @param session HttpSession
     * @return posts/myPosts
     */
    @GetMapping("/myPosts")
    public String myPosts(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("posts", postService.findByUserId(user.getId()));
        return "posts/myPosts";
    }

    /**
     * Удаление конкретного объявления
     *
     * @param model   Model
     * @param postId  id объявления
     * @return redirect:/posts/myPosts или errorPage
     */
    @GetMapping("delete/{postId}")
    public String deletePost(Model model, @PathVariable("postId") Integer postId) {
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление не существует!");
            return "errorPage";
        }
        postService.delete(optionalPost.get());
        return "redirect:/posts/myPosts";
    }

    /**
     * Показывает страницу редактирования конкретного объявления
     *
     * @param model   Model
     * @param postId  id объявления
     * @return posts/edit или errorPage
     */
    @GetMapping("edit/{postId}")
    public String editPost(Model model, @PathVariable("postId") Integer postId) {
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление не существует!");
            return "errorPage";
        }
        model.addAttribute("post", optionalPost.get());
        return "posts/edit";
    }

    /**
     * Меняет статус объявления на "Продано"
     *
     * @param model   Model
     * @param postId  id объявления
     * @return redirect:/posts/myPosts или errorPage
     */
    @GetMapping("isSale/{postId}")
    public String isSale(Model model, @PathVariable("postId") Integer postId) {
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление не существует!");
            return "errorPage";
        }
        postService.isSale(postId);
        return "redirect:/posts/myPosts";
    }

    /**
     * Показывает страницу с формой обновления объявления
     *
     * @param model   Model
     * @param session HttpSession
     * @param postId  id объявления
     * @return posts/update или errorPage
     */
    @GetMapping("update/{postId}")
    public String updatePost(Model model, HttpSession session, @PathVariable("postId") Integer postId) {
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление не существует!");
            return "errorPage";
        }
        model.addAttribute("post", optionalPost.get());
        session.setAttribute("post", optionalPost.get());
        return "posts/update";
    }

    /**
     * Обновляет данные из формы в БД
     *
     * @param post    объявление
     * @param carName марка авто
     * @param file    MultipartFile
     * @param model   Model
     * @param session HttpSession
     * @return redirect:/posts/myPosts или errorPage
     */
    @PostMapping("/update")
    public String update(@ModelAttribute Post post,
                         @RequestParam(value = "car.name", required = false) String carName,
                         @RequestParam("file") MultipartFile file,
                         Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
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
     * Показывает страницу с объявлениями за сутки
     *
     * @param model   Model
     * @return posts/today
     */
    @GetMapping("/today")
    public String postsForLastDay(Model model) {
        model.addAttribute("posts", postService.findForLastDay());
        return "posts/today";
    }

    /**
     * Показывает страницу с объявлениями, в которых есть фото
     *
     * @param model   Model
     * @return posts/withPhoto
     */
    @GetMapping("/withPhoto")
    public String postsWithPhoto(Model model) {
        model.addAttribute("posts", postService.findWithPhoto());
        return "posts/withPhoto";
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

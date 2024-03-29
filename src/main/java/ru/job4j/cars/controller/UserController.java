package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Контроллер пользователей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    /**
     * Показывает страницу регистрации пользователя
     *
     * @param model       Model
     * @param fail        флаг, что пользователь уже существует.
     * @param success     флаг, что регистрация успешна.
     * @return user/add
     */
    @GetMapping("/add")
    public String add(Model model,
                      @RequestParam(name = "fail", required = false) Boolean fail,
                      @RequestParam(name = "success", required = false) Boolean success) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("success", success != null);
        return "user/add";
    }

    /**
     * Добавляет данные из формы в БД.
     *
     * @param user пользователь
     * @return сообщение об успешной/неуспешной регистрации
     */
    @PostMapping("/create")
    public String create(@ModelAttribute User user) {
        Optional<User> optionalUser = userService.add(user);
        if (optionalUser.isEmpty()) {
            return "redirect:/user/add?fail=true";
        }
        return "redirect:/user/add?success=true";
    }

    /**
     * Показывает страницу авторизации пользователя.
     * Добавляет в модель атрибуты для валидации данных (fail).
     *
     * @param model Model
     * @param fail  флаг, что входные данные невалидные
     * @return user/loginPage
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model,
                            @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "user/loginPage";
    }

    /**
     * Ищет пользователя в БД. Если его там нет, то возвращает страницу с параметром fail=true.
     * Иначе переходит на страницу со всеми объявлениями.
     * Получает объект httpSession из запроса и устанавливает ей параметр "user".
     *
     * @param user пользователь
     * @param req  запрос
     * @return /posts или user/loginPage?fail=true
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userOptional = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userOptional.isEmpty()) {
            return "redirect:/user/loginPage?fail=true";
        }
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("user", userOptional.get());
        return "redirect:/posts";
    }

    /**
     * Выходит из сессии.
     *
     * @param session HttpSession
     * @return станицу авторизации пользователя.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/loginPage";
    }
}

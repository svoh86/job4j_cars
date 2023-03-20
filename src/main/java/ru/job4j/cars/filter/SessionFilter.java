package ru.job4j.cars.filter;

import org.springframework.stereotype.Component;
import ru.job4j.cars.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Класс фильтр.
 * Если пользователь не авторизован (user == null), тогда устанавливаем ему имя "Гость!".
 * Иначе устанавливает пользователя в атрибут запроса.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Component
public class SessionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpSession httpSession = req.getSession();
        addUserToRequest(httpSession, req);
        filterChain.doFilter(req, res);
    }

    private void addUserToRequest(HttpSession httpSession, HttpServletRequest req) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setLogin("Гость!");
        }
        req.setAttribute("user", user);
    }
}

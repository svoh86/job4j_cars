package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Модель данных пользователей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auto_user")
public class User {
    /**
     * Идетнификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Логин пользователя
     */
    private String login;
    /**
     * Пароль пользователя
     */
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}

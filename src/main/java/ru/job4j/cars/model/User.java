package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Модель данных пользователей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Data
@Entity
@Table(name = "auto_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
}

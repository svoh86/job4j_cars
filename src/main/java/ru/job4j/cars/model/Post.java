package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель данных объявления
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "auto_post")
public class Post {
    /**
     * Идентификатор объявления
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    /**
     * Текст объявления
     */
    private String text;
    /**
     * Дата создания объявления
     */
    @EqualsAndHashCode.Exclude
    private LocalDateTime created = LocalDateTime.now();
    /**
     * Внешний ключ на пользователя
     */
    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;
    /**
     * История изменения цены
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PriceHistory> priceHistory;
    /**
     * Подписки на объявление
     */
    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> participates = new ArrayList<>();
    /**
     * Внешний ключ на автомобиль
     */
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
    /**
     * Фото автомобиля
     */
    private byte[] photo;

    public Post(String text) {
        this.text = text;
    }
}

# Приложение "АвтоМаг"

[![Java CI with Maven](https://github.com/svoh86/job4j_cars/actions/workflows/maven.yml/badge.svg)](https://github.com/svoh86/job4j_cars/actions/workflows/maven.yml)

+ [О проекте](#О-проекте)
+ [Технологии](#Технологии)
+ [Требования к окружению](#Требования-к-окружению)
+ [Запуск проекта](#Запуск-проекта)
+ [Взаимодействие с приложением](#Взаимодействие-с-приложением)
+ [Контакты](#Контакты)

## О проекте

Web-приложение "АвтоМаг". Сервис для продажи машин.
Показывает список объявлений. Возможность выбора объявлений за сегодня или только с фото.
Объявление можно редактировать, удалять, помечать как проданные.
Для хранения данных используется Hibernate.
Для входа в систему пользователю необходимо зарегистрироваться

## Технологии

+ **Maven 3.8**
+ **Spring Boot 2.7.5**
+ **HTML 5**, **BOOTSTRAP 5**, **Thymeleaf 3.0.15**
+ **Hibernate 5.6.11**, **PostgreSQL 14**
+ **Тестирование:** **Liquibase 3.6.2**, **H2 2.1.214**, **AssertJ 3.23.1**
+ **Java 17**
+ **Checkstyle 3.1.2**

## Требования к окружению
+ **Java 17**
+ **Maven 3.8**
+ **PostgreSQL 14**
+ + **Hibernate 5.6.11**

## Запуск проекта
Перед запуском проекта необходимо настроить подключение к БД в соответствии с параметрами, 
указанными в src/main/resources/db.properties, или заменить на свои параметры.

Варианты запуска приложения:
1. Упаковать проект в jar архив (job4j_cars/target/job4j_cars-1.0.jar):
``` 
mvn package
``` 
Запустить приложение:
```
java -jar job4j_cars-1.0.jar 
```
2. Запустить приложение:
```
mvn spring-boot:run
```

## Взаимодействие с приложением
Начальная страница
![alt text](https://github.com/svoh86/job4j_cars/blob/master/img/start.png)

Страница просмотра объявления. Показывает данные об автомобиле и его владельце. 
Также указана история изменения цены данного автомобиля.
![alt text](https://github.com/svoh86/job4j_cars/blob/master/img/viewPost.png)

Форма добавления объявления.
![alt text](https://github.com/svoh86/job4j_cars/blob/master/img/createPost.png)

Объявления добавленные за сутки.
![alt text](https://github.com/svoh86/job4j_cars/blob/master/img/today.png)

Объявления только с фото
![alt text](https://github.com/svoh86/job4j_cars/blob/master/img/withPhoto.png)

Все объявления пользователя. Здесь можно выбрать или удалить конкретный пост. 
![alt text](https://github.com/svoh86/job4j_cars/blob/master/img/myPosts.png)

Страница просмотра своего объявления. Здесь можно изменить статус объявления на продано
или обновить объявление.
![alt text](https://github.com/svoh86/job4j_cars/blob/master/img/editPost.png)

Форма обновления объявления. 
![alt text](https://github.com/svoh86/job4j_cars/blob/master/img/updatePost.png)

Регистрация пользователя
![alt text](https://github.com/svoh86/job4j_cars/blob/master/img/addUser.png)

Удачная регистрация
![alt text](https://github.com/svoh86/job4j_cars/blob/master/img/successAdd.png)

Авторизация
![alt text](https://github.com/svoh86/job4j_cars/blob/master/img/login.png)

Неудачная авторизация
![alt text](https://github.com/svoh86/job4j_cars/blob/master/img/failLogin.png)

## Контакты

Свистунов Михаил Сергеевич

[![Telegram](https://img.shields.io/badge/Telegram-blue?logo=telegram)](https://t.me/svoh86)

Email: sms-86@mail.ru

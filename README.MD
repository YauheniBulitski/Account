Перед запуском проекта создать базу данных useraccount (PostgreSQL)
Изменить настройки в файле application.properties(src->main->java->resources) в частности:
      spring.datasource.url=,
      spring.datasource.username=,
      spring.datasource.password=
а так же FlyWay.properties(src->main->java->resources).

Стартовая страница http://localhost:8080/login
При первом запуске заполняется бд, пользователи по умолчанию:
Администратор - логин "Admin" пароль "1111a" роль - "Admin"
Пользователь - логин "User" пароль "1111b" роль - "User"

Cтэк технологий:
1. Java 11
2. Spring (Spring Boot, Spring MVC, Spring Data, Spring Security)
3. PostgreSQL,FlyWay
4. Thymeleaf,Bootstrap
5. Gradle

Описание проекта в ТЗ.pdf
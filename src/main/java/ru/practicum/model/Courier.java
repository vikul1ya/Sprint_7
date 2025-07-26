package ru.practicum.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Courier {

    private String login;
    private String password;
    private String firstName;
    private Integer id;

    // Геттеры
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public Integer getId() {
        return id;
    }

    // Сеттеры (с поддержкой цепочки)
    public Courier setLogin(String login) {
        this.login = login;
        return this;
    }

    public Courier setPassword(String password) {
        this.password = password;
        return this;
    }

    public Courier setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Courier setId(Integer id) {
        this.id = id;
        return this;
    }
}
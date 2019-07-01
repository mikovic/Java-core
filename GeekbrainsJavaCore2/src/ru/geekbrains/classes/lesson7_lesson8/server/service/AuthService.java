package ru.geekbrains.classes.lesson7_lesson8.server.service;

public interface AuthService {
    void start();
    String getNickByLoginPass(String login, String pass);
    void stop();
}


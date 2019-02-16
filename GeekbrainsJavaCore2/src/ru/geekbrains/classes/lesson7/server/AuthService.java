package ru.geekbrains.classes.lesson7.server;


public interface AuthService {

    boolean authUser(String username, String password);
}

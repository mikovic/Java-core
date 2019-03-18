package ru.geekbrains.classes.lesson7_lesson8.server;


import java.sql.SQLException;

public interface AuthService {

    boolean authUser(String username, String password) throws SQLException;

}

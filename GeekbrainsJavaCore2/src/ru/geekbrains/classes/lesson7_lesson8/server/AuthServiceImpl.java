package ru.geekbrains.classes.lesson7_lesson8.server;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AuthServiceImpl implements AuthService {

    private Map<String, String> users = new HashMap<>();
    private DBHandler dbHandler;

    public AuthServiceImpl(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    //  public AuthServiceImpl(DBHandler dbHandler) {
    //      users.put("ivan", "123");
    //      users.put("petr", "345");
    //     users.put("roma", "678");
    //       users.put("dana", "89");
    //  }

    @Override
    public boolean authUser(String username, String password) throws SQLException {
        //     String pwd = users.get(username);
        //     return pwd != null && pwd.equals(password);
        return dbHandler.authUser(username, password);
    }
}

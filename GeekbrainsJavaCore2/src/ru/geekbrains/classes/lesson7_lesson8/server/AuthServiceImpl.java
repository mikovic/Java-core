package ru.geekbrains.classes.lesson7_lesson8.server;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AuthServiceImpl implements AuthService {

    /*   public Map<String, String> users = new HashMap<>();

       public AuthServiceImpl() {
           users.put("ivan", "123");
           users.put("petr", "345");
           users.put("roma", "678");
           users.put("dana", "89");
       }

       @Override
       public boolean authUser(String username, String password) {

           String pwd = users.get(username);
           return pwd != null && pwd.equals(password);
       } */
    private DBHandler dbHandler;

    public AuthServiceImpl(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void setDbHandler(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    public boolean authUser(String username, String password) throws SQLException {

        return dbHandler.authUser(username,password );
    }



}

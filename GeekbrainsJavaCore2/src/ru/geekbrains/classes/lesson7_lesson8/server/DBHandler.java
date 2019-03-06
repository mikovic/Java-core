package ru.geekbrains.classes.lesson7_lesson8.server;

import org.sqlite.JDBC;
import ru.geekbrains.classes.lesson7_lesson8.client.MessageHisory;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class DBHandler implements AuthService, Closeable {


    private static DBHandler instance = null;
    private List<MessageHisory> history = Collections.synchronizedList(new ArrayList<>());


    public static synchronized DBHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DBHandler();
        return instance;
    }

    private static final String url = "jdbc:sqlite:" +
            "C:/Java-core/GeekbrainsJavaCore2/src/ru/geekbrains/classes/lesson7_lesson8/server/database.db";
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet rs;
    public static PreparedStatement ps;

    private DBHandler() throws SQLException {
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных


    }

    public void readDB() {
        try {
            this.conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("INSERT INTO USERS( NAME, PASSWORD) VALUES(?, ?);");
            ps.setString(1, "ivan");
            ps.setString(2, "234");
            ps.addBatch();
            ps.setString(1, "petr");
            ps.setString(2, "345");
            ps.addBatch();
            ps.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            System.out.println("USERS уже существуют в базе");
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public boolean authUser(String username, String password) {
        String name = null;
        String pwd = null;
        try {
            this.conn = DriverManager.getConnection(url);
            ps = conn.prepareStatement("SELECT * FROM USERS WHERE NAME= ? AND PASSWORD = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            boolean records = false;
            while (rs.next()) {
                records = true;
                rs.getString("ID");
                name = rs.getString("NAME");
                pwd = rs.getString("PASSWORD");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                rs.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        return username.equals(name) && password.equals(pwd);

    }


    public void changePassword(String userName, String password) {

        try {
            this.conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("update  USERS set PASSWORD = ? WHERE   NAME= ?");
            ps.setString(1, password);
            ps.setString(2, userName);
            ps.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void close() throws IOException {
        try {
            ps.close();
            statmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void adddHistory(String username, String date, String message) {
        try {
            this.conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("INSERT INTO HISTORY( USER, DATE, MESSAGE) VALUES(?, ?, ?);");
            ps.setString(1, username);
            ps.setString(2, date);
            ps.setString(3, message);
            ps.addBatch();
            ps.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            System.out.println("Не удалось добавить в историю");
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public List<MessageHisory> selectHisory() {
        String userName;
        String date;
        String message;

        try {
            this.conn = DriverManager.getConnection(url);
            statmt = conn.createStatement();
            rs = statmt.executeQuery("SELECT * FROM HISTORY ORDER BY ID DESC LIMIT 50");

            boolean records = false;
            while (rs.next()) {
                records = true;
                rs.getString("ID");
                userName = rs.getString("USER");
                date = rs.getString("DATE");
                message = rs.getString("MESSAGE");
                this.history.add(new MessageHisory(userName, date, message));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                rs.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this.history;
    }

}

package ru.geekbrains.classes.lesson7_lesson8.server;

import org.sqlite.JDBC;

import java.sql.*;

public class DBHandler implements AuthService {


    private static DBHandler instance = null;

    public static synchronized DBHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DBHandler();
        return instance;
    }

    private static final String url ="jdbc:sqlite:"+
            "C:/Progects/Java-core/GeekbrainsJavaCore2/src/ru/geekbrains/classes/lesson7_lesson8/server/database.db";
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;
    public static PreparedStatement ps;

    private DBHandler() throws SQLException {
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.conn = DriverManager.getConnection(url);

    }

    public void createDB() throws SQLException {
        statmt = this.conn.createStatement();
        String sql;
        sql = "CREATE TABLE IF NOT EXISTS USERS( ID         INTEGER, NAME       TEXT NOT NULL, PASSWORD VARCHAR(50))";
        statmt.executeUpdate(sql);
        sql ="CREATE TABLE IF NOT E"
        statmt.close();

    }
    public void fiillDB() throws SQLException {
        conn.setAutoCommit(false);
        PreparedStatement ps = conn.prepareStatement("INSERT INTO USERS(ID, NAME, PASSWORD) VALUES(?, ?, ?);");
        ps.setInt(1, 1);
        ps.setString(2, "ivan");
        ps.setString(3, "123");
        ps.addBatch();
        ps.setInt(1, 2);
        ps.setString(2, "petr");
        ps.setString(3, "345");
        ps.addBatch();
        ps.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);

    }


    @Override
    public boolean authUser(String username, String password) throws SQLException {
        String name = null;
        String pwd = null;
        this.conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false);
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM USERS WHERE NAME= ? AND PASSWORD = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            name = rs.getString(name);
            pwd = rs.getString(password);
        }
        return username.equals(name) && password.equals(pwd);
    }
    public
}

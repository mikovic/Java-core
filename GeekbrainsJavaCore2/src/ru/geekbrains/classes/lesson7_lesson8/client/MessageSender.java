package ru.geekbrains.classes.lesson7_lesson8.client;

import java.util.ArrayList;

public interface MessageSender {
    public void addUser(String user);
    public void submitMessage ( String name, String message);
    public void setListUsers();
}

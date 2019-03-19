package ru.geekbrains.classes.lesson7_lesson8.client;

public class MessageHisory {
    private String userName;

    private String message;
    private String date;
    public MessageHisory(String userName, String date, String message) {
       this.userName =userName;
        this.date = date;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}

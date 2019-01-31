package ru.geekbrains.classes.collections;

import java.util.ArrayList;

public class Contact {
    private  String name;
    private ArrayList<String> list = new ArrayList<String>();

    public Contact(String name) {
        this.name = name;
    }

    public void addPhone(String phoneNumber){
        this.list.add(phoneNumber);
    }

    public ArrayList<String> getList() {
        return list;
    }

    public String getName() {
        return name;
    }
}

package ru.geekbrains.classes.lesson7.server;

import ru.geekbrains.classes.sockets.chat.server.Connect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerApp {
    public static List<ClientHandler> connects = Collections.synchronizedList(new ArrayList<ClientHandler>());

    public static void main(String[] args) {

        new MyServer();
    }

}

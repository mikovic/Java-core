package ru.geekbrains.classes.lesson7.server;

import ru.geekbrains.classes.lesson7.server.service.AuthService;
import ru.geekbrains.classes.lesson7.server.service.BaseAuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    private final int PORT = 8185;
    private List<ClientHandler> clients;
    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }

    public MyServer() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();

            while (true) {
                System.out.println("Сервер ожидает подключения");
                Socket socket = server.accept();

                ClientHandler clientHandler =new ClientHandler(this, socket);
                System.out.println("Клиент подключился");

            }
        } catch (IOException e) {
            System.out.println("Ошибка в работе сервера");
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler handler : ServerApp.connects) {
            if (handler.getName().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void broadcastMsg(String msg) {
        for (ClientHandler handler : clients) {
            handler.sendMsg(msg);
        }
    }
    public synchronized void subscribe(ClientHandler handler) {
        clients.add(handler);
    }

    public synchronized void unsubscribe(ClientHandler handler) {
        clients.remove(handler);
    }


}


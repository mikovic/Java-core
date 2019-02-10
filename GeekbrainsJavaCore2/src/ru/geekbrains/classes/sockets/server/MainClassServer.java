package ru.geekbrains.classes.sockets.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class MainClassServer {
    public static void main(String[] args) {

        ServerSocket serv = null;
        Socket socket = null;

        try {
            serv = new ServerSocket(8189);
            System.out.println("Сервер запущен, ожидаем подключения...");
            socket = serv.accept();


            System.out.println("Клиент подключился");
            DataInputStream input = new DataInputStream(socket.getInputStream());
           DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String userName = input.readUTF();
                String msg = input.readUTF();
                if (msg.equals("end")) break;
                output.writeUTF("Сервер клиенту "+userName);
                output.writeUTF("Эхо: " + msg);
                output.flush();
            }
        } catch (IOException e) {
            System.out.println("Ошибка инициализации сервера");
        } finally {
            try {
                serv.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}

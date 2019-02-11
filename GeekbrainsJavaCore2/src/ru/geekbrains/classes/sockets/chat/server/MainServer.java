package ru.geekbrains.classes.sockets.chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {

    static public ArrayList<Thread> listThread = new ArrayList<>();
    static public Thread thread;
    public static void main(String[] args) {


        try (ServerSocket serverSocket = new ServerSocket(8180)) {
            System.out.println("Server started!");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");


                thread =new Thread(new Runnable() {
                    @Override
                    public void run() {

                            ClientServer clientServer =new ClientServer(socket);
                            while (true) {
                                try {
                                    clientServer.sendEho();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }


                    }
                });
                thread.start();


            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

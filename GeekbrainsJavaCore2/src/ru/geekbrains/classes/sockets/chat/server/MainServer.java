package ru.geekbrains.classes.sockets.chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainServer {


    public static void main(String[] args) {
        List<Connect> connects;
        connects = Collections.synchronizedList(new ArrayList<Connect>());

        Thread thread;
        try (ServerSocket serverSocket = new ServerSocket(8180)) {
            System.out.println("Server started!");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                            Connect connect = new Connect(socket);
                            synchronized (connects){
                                connects.add(connect);
                            }

                            while (true) {
                                try {

                                    connect.sendEho();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                }).start();
                
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
                        String msg;

                        try {
                            while ((msg = console.readLine()) != null) {

                                    for (Connect connect : connects) {
                                        connect.readMsgFromConsole(msg);
                                    }
                                
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

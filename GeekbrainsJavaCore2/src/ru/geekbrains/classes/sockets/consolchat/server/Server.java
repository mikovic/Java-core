package ru.geekbrains.classes.sockets.consolchat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {


    public static void main(String[] args) throws IOException {

        Socket socket;
        BufferedReader console = null;

        try (ServerSocket serverSocket = new ServerSocket(7777)) {
            System.out.println("Server started!");

            socket = serverSocket.accept();
            Con con = new Con(socket);
            System.out.println("Client connected!");
            con.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            con.readMsg();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            con.sendMsg();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    static class Con implements Cloneable {
        BufferedReader in;
        PrintWriter out;
        ServerSocket serverSocket;
        Socket socket;

        BufferedReader console = null;

        public Con(Socket socket) {
            this.socket = socket;
        }

        void start() throws IOException {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            console = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Вводите:");
        }

        public void sendMsg() throws IOException {
            String msg = null;
            while (true) {
                if ((msg = console.readLine()) != null) {
                    out.println("Server: " + msg);
                }
            }

        }

        public void readMsg() throws IOException {
            while (true) {
                String msg = null;
                if ((msg = in.readLine()) != null) {
                    System.out.println(msg);
                    if ((msg.substring(msg.trim().length()-3)).equalsIgnoreCase("end")) break;
                }
            }
        }


        void close() throws IOException {

            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
            try {
                console.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket.close();
            serverSocket.close();
        }

    }

}

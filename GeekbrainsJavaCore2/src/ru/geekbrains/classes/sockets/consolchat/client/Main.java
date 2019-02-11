package ru.geekbrains.classes.sockets.consolchat.client;

import java.io.*;
import java.net.Socket;

public class Main {





        public static void main(String[] args) throws IOException {
            final String SERVER_ADDR = "localhost";
            final int SERVER_PORT = 7777;
            Socket socket = new Socket(SERVER_ADDR, SERVER_PORT);

            Client client = new Client(socket);
            client.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        client.readMsg();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        client.sendMsg();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();



        }
    static class Client implements Closeable {

        BufferedReader in;
        PrintWriter out;
        BufferedReader console;
        Socket socket;

        public Client(Socket socket) {
            this.socket = socket;
        }
        void start() throws IOException {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            console = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Готoво.Можете отсылать сообщения.");
        }

        public void sendMsg() throws IOException {
            String msg = null;
            while (true) {

                if((msg = console.readLine())!=null){
                    out.println("Client:" +msg);
                    if (msg.equalsIgnoreCase("end"))break;

                }
            }

        }
        public void readMsg() throws IOException {
            while (true) {
                String msg = null;
                if ((msg = in.readLine()) != null){
                    System.out.println(msg);
                    if ((msg.substring(msg.trim().length()-3)).equalsIgnoreCase("end")) break;
                }
            }
        }

        @Override
        public void close() throws IOException {
            out.close();
            in.close();
            socket.close();
            socket.close();
        }
    }




}




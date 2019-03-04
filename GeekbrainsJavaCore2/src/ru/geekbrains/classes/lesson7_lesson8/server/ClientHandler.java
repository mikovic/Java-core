package ru.geekbrains.classes.lesson7_lesson8.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.regex.Pattern;


public class ClientHandler {

    private static final Pattern MESSAGE_PATTERN = Pattern.compile("^/w (.+) (.+)$");
    private final Thread handleThread;
    private final DataInputStream inp;
    private final DataOutputStream out;
    private final ChatServer server;
    private final String username;
    private final Socket socket;
    private static final Pattern SEND_PATTERN = Pattern.compile("^/send (.+) (.+)$");
    private static final Pattern SENDTO_PATTERN = Pattern.compile("^/sendto (.+) (.+) (.+)$");


    public ClientHandler(String username, Socket socket, ChatServer server) throws IOException {
        this.username = username;
        this.socket = socket;
        this.server = server;
        this.inp = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());


        this.handleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        String message = inp.readUTF();
                        if (message.startsWith("/send")) {
                            System.out.printf("Message from user %s: %s%n", username, message);
                            server.sendMessage(message);
                        }
                        if (message.startsWith("/mail")) {
                            System.out.println(message);
                            server.sendMessageTo(message);
                        }
                        if (message.startsWith(("/list"))) ;
                        System.out.println(message);
                        server.getListUsers();
                        System.out.println(server.getListUsers());
                        sendList(server.getListUsers());
                        if (message.startsWith("/changepwd")) {
                            System.out.println(message);
                            server.changePassword(message);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    System.out.printf("Client %s disconnected%n", username);
                    server.unsubscribeClient(username);

                }
            }

        });
        handleThread.start();
    }
    public void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getUsername() {
        return username;
    }

    public void sendList(String[] listUsers) throws IOException {
        int len = listUsers.length;
        if (len == 1) {
            out.writeUTF("/finish " + listUsers[0]);
        }else {
            for (int i = 0; i < len - 1; i++) {
                out.writeUTF("/list " + listUsers[i]);
            }
            out.writeUTF(("/finish " + listUsers[len - 1]));
        }

    }



}
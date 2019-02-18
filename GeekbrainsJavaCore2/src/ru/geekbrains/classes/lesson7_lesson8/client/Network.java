package ru.geekbrains.classes.lesson7_lesson8.client;

import ru.geekbrains.classes.lesson7_lesson8.client.MessageSender;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;

public class Network implements Closeable {
    private static final String AUTH_PATTERN = "/auth %s %s";
    private static final Pattern SEND_PATTERN = Pattern.compile("^/send (.+) (.+)$");
    private final Socket socket;
    private final DataOutputStream out;
    private final DataInputStream in;
    private final MessageSender messageSender;
    private final Thread receiver;
    private HashSet<String> set;
    private String username;
    public Network(String hostName, int port, MessageSender messageSender) throws IOException {
        this.socket = new Socket(hostName, port);
        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
        this.messageSender = messageSender;
        this.set = new HashSet<>();
        this.receiver = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        String msg = in.readUTF();
                        if (msg.startsWith("/send")) {
                            String[] parts = msg.split("\\s");
                            messageSender.submitMessage(parts[1], parts[2]);
                        } else if (msg.startsWith("/mail")) {
                            String[] parts = msg.split("\\s");
                            messageSender.submitMessage(parts[2] + "(лично)", parts[3]);
                        } else if (msg.startsWith("/rmvuser")) {
                            String[] parts = msg.split("\\s");
                            messageSender.submitMessage(parts[1], parts[2]);
                            messageSender.removeUser(parts[1]);
                        } else if (msg.startsWith("/conectuser")) {
                            String[] parts = msg.split("\\s");
                            messageSender.submitMessage(parts[1], parts[2]);
                            messageSender.addUser(parts[1]);
                        } else if (msg.startsWith("/list")) {
                            String userName = msg.substring(msg.lastIndexOf(" ") + 1);
                            set.add(userName);
                            messageSender.addUser(userName);
                            System.out.printf("Message from user %s: %s%n", username, msg);

                        } else if (msg.startsWith("/finish")) {
                            String userName = msg.substring(msg.lastIndexOf(" ") + 1);
                            set.add(userName);
                            for(String str : set) {
                                messageSender.addUser(userName);
                            }
                            System.out.printf("Message from user %s: %s%n", username, msg);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.printf("Client %s disconnected%n", username);

                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    public void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void authorize(String username, String password) {
        try {
            out.writeUTF(String.format(AUTH_PATTERN, username, password));
            out.flush();
            String response = in.readUTF();
            if (response.equals("/auth successful")) {
                this.username = username;
                receiver.start();
            } else {
                throw new AuthException("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void close() throws IOException {
        socket.close();
        receiver.interrupt();
        try {
            receiver.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


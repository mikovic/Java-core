package ru.geekbrains.classes.sockets.client;

import ru.geekbrains.classes.sockets.Message;
import ru.geekbrains.classes.sockets.MessageSender;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Closeable {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    private Socket sock;
    private Scanner in;
    private PrintWriter out;
    private final MessageSender messageSender;

    Thread t;


    public Client(MessageSender messageSender) {
        this.messageSender = messageSender;

        try {
            sock = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new Scanner(new DataInputStream(sock.getInputStream()));
            out = new PrintWriter(new DataOutputStream(sock.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (in.hasNext()) {
                            String userName = in.nextLine();
                            String msg = in.nextLine();
                            if (msg.equalsIgnoreCase("end session")) break;
                            messageSender.submitMessage(userName, msg);
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
        t.start();
    }
    public void sendMsg( Message message){
        out.println(message.getUserName());
        out.println(message.getMessage());
        out.flush();

    }

    @Override
    public void close() throws IOException {
        t.interrupt();
        sock.close();
    }
}

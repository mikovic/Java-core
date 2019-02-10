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
    private DataInputStream in;
    private  DataOutputStream out;
    private final MessageSender messageSender;

    Thread t;


    public Client(MessageSender messageSender) {
        this.messageSender = messageSender;

        try {
            sock = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new DataInputStream(sock.getInputStream());
            out = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {


                            String userName = in.readUTF();
                            String msg = in.readUTF();
                            if (msg.equalsIgnoreCase("end session")) break;
                            messageSender.submitMessage(userName, msg);

                    }
                } catch (Exception e) {
                }
            }
        });
        t.start();
    }
    public void sendMsg( Message message)  {
        try {
            out.writeUTF(message.getUserName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.writeUTF(message.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void close() throws IOException {
        t.interrupt();
        sock.close();
    }
}

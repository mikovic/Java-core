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

    private final MessageSender messageSender;
    DataInputStream in;
    DataOutputStream out;

    Thread t;


    public Client(MessageSender messageSender) {
        this.messageSender = messageSender;

        try {
            sock = new Socket(SERVER_ADDR, SERVER_PORT);
             in = new DataInputStream(sock.getInputStream());
            out  = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        String userName = in.readUTF();
                        String msg = in.readUTF();
                        if (msg.equalsIgnoreCase("end session")) break;
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("New message " + msg);
                                messageSender.submitMessage(userName, msg);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }
    public void sendMsg( Message message){
        try {
            out.writeUTF(message.getUserName());
            out.writeUTF(message.getUserName());
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

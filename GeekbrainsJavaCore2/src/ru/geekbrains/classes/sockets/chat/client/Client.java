package ru.geekbrains.classes.sockets.chat.client;

import ru.geekbrains.classes.sockets.Message;
import ru.geekbrains.classes.sockets.MessageSender;

import java.io.*;
import java.net.Socket;

public class Client implements Closeable {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8180;
    private Socket sock;
    private DataInputStream in;
    private  DataOutputStream out;
    private PrintWriter output;
    private final MessageSender messageSender;

    Thread thread;


    public Client(MessageSender messageSender) {
        this.messageSender = messageSender;

        try {
            sock = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new DataInputStream(sock.getInputStream());
            out = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.thread = new Thread(new Runnable() {
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
                     e.printStackTrace();
                }
            }
        });
        thread.start();

    }
    public void sendMsg( Message message)  {
        try {
            out.writeUTF(message.getUserName());
             out.writeUTF(message.getMessage());
             out.writeUTF(message.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }        

    }

    @Override
    public void close() throws IOException {
        thread.interrupt();
        sock.close();
    }
}

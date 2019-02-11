package ru.geekbrains.classes.sockets.server;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientServer implements Runnable, Closeable {

    Socket socket;
    Thread thread;
    DataInputStream in = null;
    DataOutputStream out = null;

    public ClientServer(Socket socket) {
        this.socket = socket;
        thread = new Thread(this);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Поток запущен");

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                String userName = in.readUTF();
                String msg = in.readUTF();
                if (msg.equals("end")) break;
                out.writeUTF("Сервер:" + userName);
                out.writeUTF("Эхо: " + msg);
                out.flush();

            }

        } catch (IOException e) {
            System.out.println("Поток прерван ");

        }


    }
    public void sendMsg(String msg) throws IOException {
        out.writeUTF(msg);
        out.flush();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}

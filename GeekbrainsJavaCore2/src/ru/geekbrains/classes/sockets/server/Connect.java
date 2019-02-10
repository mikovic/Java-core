package ru.geekbrains.classes.sockets.server;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connect implements Runnable, Closeable {

    Socket socket;
    Thread thread;

    public Connect(Socket socket){
        this.socket = socket;
        new Thread(this).start();

    }
    @Override
    public void run() {
        System.out.println("Поток запущен");
        DataInputStream in = null;
        DataOutputStream out = null;
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

    @Override
    public void close() throws IOException {
        socket.close();
    }
}

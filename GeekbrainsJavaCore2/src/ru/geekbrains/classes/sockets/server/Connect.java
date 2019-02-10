package ru.geekbrains.classes.sockets.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connect implements Runnable {

    Socket socket;
    Thread thread;

    public Connect(Socket socket){
        this.socket = socket;
        new Thread(this).start();

    }
    @Override
    public void run() {
        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                String userName = in.readUTF();
                if (userName.equals("end")) break;
                out.writeUTF("Сервер:" + userName);
                String str = in.readUTF();
                out.writeUTF("Эхо: " + str);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

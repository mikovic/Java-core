package ru.geekbrains.classes.sockets.chat.server;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connect implements  Closeable {

    DataInputStream in;
    DataOutputStream out;
    Socket socket;

    public Connect(Socket socket){

        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }


}

    public  void sendEho() throws IOException, InterruptedException {

            String userName = in.readUTF();
            String msg = in.readUTF();
            if (msg.equals("end")) close();
            out.writeUTF("Сервер:" + userName);
            out.writeUTF("Эхо: " + msg);
            out.flush();



    }
    public  void readMsgFromConsole(String msg) throws IOException {
        out.writeUTF("Сервер");
        out.writeUTF(msg);
        out.flush();
      ;

    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}

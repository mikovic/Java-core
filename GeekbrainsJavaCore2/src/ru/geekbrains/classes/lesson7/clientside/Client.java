package ru.geekbrains.classes.lesson7.clientside;

import ru.geekbrains.classes.sockets.MessageSender;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8185;
    private static final String AUTH_PATTERN = "/auth %s %s";
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private  Thread thread;
    MyWindow myWindow;
    private String username;

    public Client(MyWindow myWindow) throws IOException {
        this.myWindow = myWindow;
        this.socket = new Socket(SERVER_ADDR, SERVER_PORT);
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

    }
    public void openConnection() throws IOException {

          thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String strFromServer = in.readUTF();
                        if (strFromServer.equalsIgnoreCase("/end")) {
                            break;
                        }
                        myWindow.getChatArea().append(strFromServer);
                        myWindow.getChatArea().append("\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void authorize(String username, String password) {
        try {
            String str ="/auth " + username +" "+ password;
            out.writeUTF(str);
            String response = in.readUTF();
            if (response.startsWith("/authok")) {
                String nick = response.substring(response.lastIndexOf(" ")+1);
                this.username = nick;
                thread.start();

            } else {
                throw new AuthException("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage() {
        if (!myWindow.getMsgInputField().getText().trim().isEmpty()) {
            try {
                out.writeUTF(myWindow.getMsgInputField().getText());
                myWindow.getMsgInputField().setText("");
                myWindow.getMsgInputField().grabFocus();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения");
            }
        }
    }


    public void closeConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataOutputStream getOut() {
        return out;
    }

    public MyWindow getMyWindow() {
        return myWindow;
    }

    public String getUsername() {
        return username;
    }


}

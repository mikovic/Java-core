package ru.geekbrains.classes.lesson7.clientside;

import ru.geekbrains.classes.sockets.Message;
import ru.geekbrains.classes.sockets.MessageSender;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyWindow extends JFrame {

    private JTextField msgInputField;
    private JTextArea chatArea;
    private Client client;


    public JTextArea getChatArea() {
        return chatArea;
    }

    public Client getClient() {
        return client;
    }

    public JTextField getMsgInputField() {
        return msgInputField;
    }

    public MyWindow() throws IOException {

        setBounds(600, 300, 500, 500);

        setTitle("Клиент");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Текстовое поле для вывода сообщений
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        JMenuBar mainMenu = new JMenuBar();
        JButton btnSignIn = new JButton("Sign in");
        JButton btnSignUp = new JButton("Sign up");
        JButton btnSend = new JButton("Send ALL");
        JButton btnSelect = new JButton("Select interlocutor");
        setJMenuBar(mainMenu);
        mainMenu.add(btnSignUp);
        mainMenu.add(btnSignIn);
        mainMenu.add(btnSend);
        mainMenu.add(btnSelect);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Нижняя панель с полем для ввода сообщений и кнопкой отправки сообщений
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton btnSendMsg = new JButton("Отправить");
        JLabel labelChat = new JLabel("Всем ");
        bottomPanel.add(btnSendMsg, BorderLayout.EAST);
        bottomPanel.add(labelChat, BorderLayout.WEST);
        this.msgInputField = new JTextField();
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(msgInputField, BorderLayout.CENTER);


        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelChat.setText("Rere");

            }
        });
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelChat.setText("Ehf");
            }
        });

        btnSendMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage();
            }
        });
        msgInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage();
            }
        });

        // Настраиваем действие на закрытие окна
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    client.getOut().writeUTF("/end");
                    client.closeConnection();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });


        setVisible(true);
        LoginDialog loginDialog = new LoginDialog(this);
        loginDialog.setVisible(true);

        if (!loginDialog.isAuthSuccessful()) {
            System.exit(0);
        }
        client = loginDialog.getClient();
        setTitle("Сетевой чат. Пользователь " + client.getUsername());
    }


}





package ru.geekbrains.classes.sockets.client;

import ru.geekbrains.classes.sockets.Message;
import ru.geekbrains.classes.sockets.MessageSender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyWindow extends JFrame implements MessageSender {

    private JTextField jtf;
    private JTextArea jta;
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    private Socket sock;
    private Scanner in;
    private PrintWriter out;
    Client client;
    Message message;

    public MyWindow(String userName) {

        setBounds(600, 300, 500, 500);
        setTitle("Client "+userName);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jta = new JTextArea();
        jta.setEditable(false);
        jta.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(jta);
        add(jsp, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        JButton jbSend = new JButton("SEND");
        bottomPanel.add(jbSend, BorderLayout.EAST);
        jtf = new JTextField();
        bottomPanel.add(jtf, BorderLayout.CENTER);
        jbSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = jtf.getText().trim();
                if (!msg.isEmpty()) {
                    client.sendMsg(new Message(userName, msg));
                    jtf.setText("");
                    jtf.grabFocus();
                }
            }
        });
        jtf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMsg(new Message(userName, jtf.getText()));

            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                client.sendMsg(new Message(userName, "end"));
                try {

                    client.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            }
        });
        client = new Client(this);
        setVisible(true);
    }



    @Override
    public void submitMessage(String user, String msg) {
        if (msg == null || msg.isEmpty()) {
            return;
        }
        jta.append(user);
        jta.append("\n");
        jta.append(msg+"\n");
    }
}

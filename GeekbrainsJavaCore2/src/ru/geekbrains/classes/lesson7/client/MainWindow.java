package ru.geekbrains.classes.lesson7.client;

import ru.geekbrains.classes.sockets.MessageSender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainWindow extends JFrame implements MessageSender {

    private JTextField textField;
    private JButton button;
    private JScrollPane scrollPane;
    private JList<Message> list;
    private DefaultListModel<Message> listModel;
    private JPanel panel;
    private JMenuBar mainMenu;
    private JButton btnSignin;
    private JButton btnSignUp;
    private JButton btnSend;
    private JButton btnSelect;
    private JButton jBtnLabel;
    private String users[] = {"Ivan", "Петр"};
    private WindowSelect windowSelect;

    public String[] getUsers() {
        return users;
    }

    public JButton getjBtnLabel() {

        return jBtnLabel;

    }

    public void setUsers(String[] users) {
        this.users = users;
    }

    private Network network;


    public MainWindow() {
        setTitle("Сетевой чат");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(200, 200, 500, 500);

        setLayout(new BorderLayout());   // выбор компоновщика элементов

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setCellRenderer(new MessageCellRenderer());
        JMenuBar mainMenu = new JMenuBar();
        JButton btnSignIn = new JButton("Sign in");
        JButton btnSignUp = new JButton("Sign up");
        JButton btnSelect = new JButton("USERS");
        setJMenuBar(mainMenu);
        mainMenu.add(btnSignUp);
        mainMenu.add(btnSignIn);
        mainMenu.add(btnSelect);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(list, BorderLayout.SOUTH);
        panel.setBackground(list.getBackground());
        scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        textField = new JTextField();
        button = new JButton("Send");
        jBtnLabel = new JButton("ALL");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((jBtnLabel.getText() =="ALL")){
                    String text = textField.getText();
                    submitMessage(network.getUsername(), text);
                    textField.setText(null);
                    textField.requestFocus();
                    text = "/send " + network.getUsername() + " " + text;
                    network.sendMessage(text);
                }else {
                    String text = textField.getText().trim();
                    text = "/sendto " + jBtnLabel.getText().trim() + " "+ network.getUsername() + " " + text;
                    network.sendMessage(text);
                }


            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                list.ensureIndexIsVisible(listModel.size() - 1);
            }
        });

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(jBtnLabel, BorderLayout.WEST);
        panel.add(button, BorderLayout.EAST);
        panel.add(textField, BorderLayout.CENTER);
        jBtnLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jBtnLabel.setText("ALL");
            }
        });
        add(panel, BorderLayout.SOUTH);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (network != null) {
                        network.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                super.windowClosing(e);
            }
        });

        WindowSelect windowSelect = new WindowSelect(this);
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowSelect.setVisible(true);

            }
        });

        setVisible(true);

        LoginDialog loginDialog = new LoginDialog(this);
        loginDialog.setVisible(true);

        if (!loginDialog.isAuthSuccessful()) {
            System.exit(0);
        }
        this.network = loginDialog.getNetwork();
        setTitle("Сетевой чат. Пользователь " + this.network.getUsername());
    }

    @Override
    public void submitMessage(String user, String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        Message msg = new Message(user, message);
        listModel.add(listModel.size(), msg);
        list.ensureIndexIsVisible(listModel.size() - 1);

    }
}

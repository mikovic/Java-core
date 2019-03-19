package ru.geekbrains.classes.lesson7_lesson8.client;


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
    private JLabel jLabel;
    private WindowSelect windowSelect;
    private WindowHistory windowHistory;


    public Network network;


    public MainWindow() throws IOException {
        setTitle("Сетевой чат");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(200, 200, 500, 500);
        setLayout(new BorderLayout());   // выбор компоновщика элементов
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setCellRenderer(new MessageCellRenderer());
        JMenuBar mainMenu = new JMenuBar();
        JButton btnChangePwd = new JButton("Change Password");
        JButton btnSignUp = new JButton("Sign up");
        JButton btnSend = new JButton("Chose All");
        JButton btnSelect = new JButton("Chose User");
        JButton btnHistory = new JButton("History");
        setJMenuBar(mainMenu);
        mainMenu.add(btnSignUp);
        mainMenu.add(btnChangePwd);
        mainMenu.add(btnSend);
        mainMenu.add(btnSelect);
        mainMenu.add(btnHistory);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(list, BorderLayout.SOUTH);
        panel.setBackground(list.getBackground());
        scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
        textField = new JTextField();
        button = new JButton("Send");
        jLabel = new JLabel("ALL");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((jLabel.getText() == "ALL")) {
                    String text = textField.getText();
                    submitMessage(network.getUsername(), text);
                    textField.setText(null);
                    textField.requestFocus();
                    text = "/send//" + network.getUsername() + "//" + text;
                    network.sendMessage(text);

                } else {
                    String text = textField.getText().trim();
                    text = "/mail//" + jLabel.getText().trim() + " " + network.getUsername() + "//" + text;
                    textField.setText(null);
                    network.sendMessage(text);
                    windowSelect.setVisible(false);
                }
            }

        });
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jLabel.setText("ALL");
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
        panel.add(jLabel, BorderLayout.WEST);
        panel.add(button, BorderLayout.EAST);
        panel.add(textField, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        windowSelect = new WindowSelect(this);
        windowHistory =new WindowHistory(this);
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

        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowSelect.setVisible(true);

            }
        });
        btnHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = "/history "  + network.getUsername();
                 network.sendMessage(text);
                 windowHistory.setVisible(true);


            }
        });

        setVisible(true);
        network = new Network("localhost", 777, this);
        LoginDialog loginDialog = new LoginDialog(this, network);
        loginDialog.setVisible(true);
        if (!loginDialog.isAuthSuccessful()) {
            System.exit(0);
        }
        this.network = loginDialog.getNetwork();
        setTitle("Сетевой чат. Пользователь " + this.network.getUsername());

        ChangeDialog changeDialog = new ChangeDialog(this,network);
        btnChangePwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeDialog.setVisible(true);

            }
        });
    }


    @Override
    public void addUser(String user) {
        if (!windowSelect.listModel.contains(user)) {
            windowSelect.listModel.addElement(user);
        } else {
            return;
        }
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

    @Override
    public void removeUser(String user) {
        if (windowSelect.listModel.contains(user)) {
            windowSelect.listModel.removeElement(user);
        } else {
            return;
        }
    }

    @Override
    public void addHistory(String nameTime, String message) {
        windowHistory.listModel.addElement(nameTime);
        windowHistory.listModel.addElement(message);
    }

    public JLabel getjLabel() {
        return jLabel;
    }
}


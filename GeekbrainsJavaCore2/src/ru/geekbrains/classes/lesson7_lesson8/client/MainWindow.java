package ru.geekbrains.classes.lesson7_lesson8.client;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;


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
    private String users[] = {"ivan","petr","roma"};
    private WindowSelect windowSelect;
    private ArrayList<String> listUsers = new ArrayList<>();

    public String[] getUsers() {
        return users;
    }


    public void setUsers(String[] users) {
        this.users = users;
    }

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
        JButton btnSignIn = new JButton("Sign in");
        JButton btnSignUp = new JButton("Sign up");
        JButton btnSend = new JButton("Chose All");
        JButton btnSelect = new JButton("Chose User");
        setJMenuBar(mainMenu);
        mainMenu.add(btnSignUp);
        mainMenu.add(btnSignIn);
        mainMenu.add(btnSend);
        mainMenu.add(btnSelect);
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
                    text = "/send " + network.getUsername() + " " + text;
                    network.sendMessage(text);

                } else {
                    String text = textField.getText().trim();
                    text = "/mail " + jLabel.getText().trim() + " " + network.getUsername() + " " + text;
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
        windowSelect.setVisible(false);

        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowSelect.setVisible(true);

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
    }

    public JLabel getjLabel() {
        return jLabel;
    }


    @Override
    public void addUser(String user) {

        this.listUsers.add(user);
        System.out.println("Добавили "+user);
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
    public void setListUsers() {

        String us[] = new String[listUsers.size()];
        this.users = listUsers.toArray(us);
        System.out.print(this.users);
        windowSelect = new WindowSelect(this);
        System.out.println(windowSelect);

    }
}

package ru.geekbrains.classes.lesson7_lesson8.client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class WindowSelect extends JFrame {
    JList jList;
    JLabel jLabel;
    JScrollPane pane;

    private String users[];


    public WindowSelect(MainWindow window) {
        setTitle("Сетевой чат");

        setBounds(200, 200, 300, 200);

        setLayout(new BorderLayout());   // выбор компоновщика э
        users = window.getUsers();
        jList = new JList(users);

        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pane = new JScrollPane(jList);
        jLabel = new JLabel("Выберете собеседника");
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int idx = jList.getSelectedIndex();
                if (idx != -1) {
                    jLabel.setText("Current selection: " + users[idx]);
                    window.getjLabel().setText(users[idx]);
                    setVisible(false);
                } else {
                    jLabel.setText("Please choose a user!");
                }
            }

        });
        add(pane, BorderLayout.CENTER);
        add(jLabel, BorderLayout.NORTH);
        setLocationRelativeTo(null);


    }

}

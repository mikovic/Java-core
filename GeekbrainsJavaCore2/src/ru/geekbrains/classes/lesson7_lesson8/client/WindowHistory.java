package ru.geekbrains.classes.lesson7_lesson8.client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class WindowHistory extends JFrame {
    JList list ;
    JLabel jLabel;
    JScrollPane pane;
    DefaultListModel listModel;

    private String users[];

    public void setUsers() {
        this.users = users;
    }

    public WindowHistory(MainWindow window) {
        setTitle("История последних сообщений");
        setBounds(200, 200, 400, 400);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());   // выбор компоновщика э
        listModel = new DefaultListModel();
        list  = new JList(listModel);
        pane = new JScrollPane(list );
        add(pane, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                setVisible(false);
            }
        });

    }
}

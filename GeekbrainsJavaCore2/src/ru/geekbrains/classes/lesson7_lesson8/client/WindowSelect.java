package ru.geekbrains.classes.lesson7_lesson8.client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class WindowSelect extends JFrame {
    JList list ;
    JLabel jLabel;
    JScrollPane pane;
    DefaultListModel listModel;

    private String users[];

    public void setUsers() {
        this.users = users;
    }

    public WindowSelect(MainWindow window) {
        setTitle("Сетевой чат");
        setBounds(200, 200, 300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());   // выбор компоновщика э
        listModel = new DefaultListModel();
        list  = new JList(listModel);
        list .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        pane = new JScrollPane(list );
        jLabel = new JLabel("Выберете собеседника");
        list .addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String idx  = listModel.getElementAt(list.getSelectedIndex()).toString();
                if (idx != "") {
                    jLabel.setText("Current selection: " + idx);
                    window.getjLabel().setText(idx);
                    setVisible(false);
                } else {
                    jLabel.setText("Please choose a user!");
                }
            }

        });
        add(pane, BorderLayout.CENTER);
        add(jLabel, BorderLayout.NORTH);
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

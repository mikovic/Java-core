package ru.geekbrains.classes.swinglesson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindow extends JFrame {
    public MyWindow(){
         DefaultListModel<String> listModel;
        JList<String> jlist;
        setTitle("Test CHAT");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 350, 400);
        setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        jlist = new JList<>(listModel);
        add(jlist,BorderLayout.CENTER);
        JPanel panel = new JPanel();
        add(panel,BorderLayout.SOUTH);
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        JTextField jTextField = new JTextField(200);
        panel.add(jTextField);
        JButton jButton = new JButton("SEND");
        panel.add(jButton);
        jTextField.requestFocus();
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.add(listModel.size(),jTextField.getText().trim());
                jTextField.setText("");


            }
        });
        jTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.add(listModel.size(),jTextField.getText().trim());
                jTextField.setText("");


            }
        });
        setVisible(true);

    }

}

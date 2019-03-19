package ru.geekbrains.classes.lesson7_lesson8.client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeDialog extends JDialog {

    private JTextField tfTextOldPwd;
    private JPasswordField pfPassword;
    private JLabel jLabelOldPwd;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean connected;
    private Network network;

    public ChangeDialog(Frame parent, Network network) {
        super(parent, "Change Password", true);
        this.connected = false;
        this.network = network;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        jLabelOldPwd = new JLabel("Old Password: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(jLabelOldPwd, cs);

        tfTextOldPwd = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfTextOldPwd, cs);

        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));

        btnLogin = new JButton("Login");
        btnCancel = new JButton("Cancel");

        JPanel bp = new JPanel();
        bp.add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(String.valueOf(tfTextOldPwd.getText()).equals(String.valueOf(pfPassword.getPassword()))){
                    JOptionPane.showMessageDialog(ChangeDialog.this,
                            "Ошибка: одинаковые поля",
                            "Смкна пароля",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    network.changePassword(network.getUsername(), String.valueOf(pfPassword.getPassword()));
                    connected = true;
                } catch (AuthException ex) {
                    JOptionPane.showMessageDialog(ChangeDialog.this,
                            "Ошибка авторизации",
                            "Авторизация",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dispose();
            }
        });

        bp.add(btnCancel);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public Network getNetwork() {
        return network;
    }

    public boolean isAuthSuccessful() {
        return network != null;
    }
}

package ru.geekbrains.classes.lesson7.clientside;

import javax.swing.*;
import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MyWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}



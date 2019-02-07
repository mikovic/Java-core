package ru.geekbrains.classes.swinglesson;

import javax.swing.*;

public class TestSwing {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyWindow();
            }
        });


    }
}

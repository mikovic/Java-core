package ru.geekbrains.classes.sockets.client;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class MainClassSocket {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
      SwingUtilities.invokeAndWait(new Runnable() {
          @Override
          public void run() {
              new MyWindow();

          }
      });
    }
}

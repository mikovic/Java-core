package ru.geekbrains.classes.sockets.chat.client;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class MainClient {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
      SwingUtilities.invokeAndWait(new Runnable() {
          @Override
          public void run() {
              new MyWindow();

          }
      });
    }
}

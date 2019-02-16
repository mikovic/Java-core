package ru.geekbrains.classes.lesson7.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatServer {

    private static final Pattern AUTH_PATTERN = Pattern.compile("^/auth (.+) (.+)$");
    private static final Pattern SEND_PATTERN = Pattern.compile("^/send (.+) (.+)$");
    private static final Pattern SENDTO_PATTERN = Pattern.compile("^/sendto (.+) (.+) (.+)$");
    private AuthService authService = new AuthServiceImpl();

    private Map<String, ClientHandler> clientHandlerMap = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start(7777);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started!");
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream inp = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("New client connected!");

                try {
                    String message = inp.readUTF();
                    Matcher matcher1 = AUTH_PATTERN.matcher(message);
                    Matcher matcher2 = SEND_PATTERN.matcher(message);
                    Matcher matcher3 = SENDTO_PATTERN.matcher(message);
                    if (matcher1.matches()) {
                        String username = matcher1.group(1);
                        String password = matcher1.group(2);
                        if (authService.authUser(username, password)) {
                            clientHandlerMap.put(username, new ClientHandler(username, socket, this));
                            out.writeUTF("/auth successful");
                            out.flush();
                            System.out.printf("Authorization for user %s successful%n", username);
                        } else {
                            System.out.printf("Authorization for user %s failed%n", username);
                            out.writeUTF("/auth fails");
                            out.flush();
                            socket.close();
                        }

                    }
                    if (matcher2.matches()) {
                        String username = matcher1.group(1);
                        Set<Map.Entry<String, ClientHandler>> set = clientHandlerMap.entrySet();
                        for (Map.Entry<String, ClientHandler> client : set) {
                            if(!username.equals(client.getKey())) {
                                sendMessage(client.getKey(), message);
                            }

                        }

                    }
                    if (matcher3.matches()) {
                        String username = matcher1.group(2);
                        sendMessage(username, message);


                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String username, String message) {
        // TODO реализовать отправку сообщения пользователю с именем username
        clientHandlerMap.get(username).sendMessage(message);
    }
}

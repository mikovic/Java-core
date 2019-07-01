package ru.geekbrains.classes.lesson7_lesson8.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatServer {
    private static final Pattern AUTH_PATTERN = Pattern.compile("^/auth (.+) (.+)$");
    private static final Pattern SEND_PATTERN = Pattern.compile("^/send//(.+)//(.+)$");
    private static final Pattern HISTORY_PATTERN = Pattern.compile("^/history (.+)$");
    private static final Pattern SENDTO_PATTERN = Pattern.compile("^/mail//(.+) (.+)//(.+)$");
    private static final Pattern CHANGEPWD_PATTERN = Pattern.compile("^/changepwd (.+) (.+)$");

    private DBHandler dbHandler;

    {
        try {
            dbHandler = DBHandler.getInstance();
            dbHandler.readDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private AuthService authService = new AuthServiceImpl(dbHandler);
    private Map<String, ClientHandler> clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
    ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start(777);
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
                    Matcher matcher = AUTH_PATTERN.matcher(message);
                    if (matcher.matches()) {
                        String username = matcher.group(1);
                        String password = matcher.group(2);
                        if (authService.authUser(username, password)) {
                            ClientHandler clientHandler = new ClientHandler(username, socket, this);
                            clientHandlerMap.put(username, clientHandler);
                            out.writeUTF("/auth successful");
                            broadcastUserConnected(username);
                            clientHandler.sendList(getListUsers());
                            out.flush();
                            System.out.printf("Authorization for user %s successful%n", username);
                        } else {
                            System.out.printf("Authorization for user %s failed%n", username);
                            out.writeUTF("/auth fails");
                            out.flush();
                            socket.close();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        String username = null;
        String text = null;
        Matcher matcher = SEND_PATTERN.matcher(message);
        if (matcher.matches()) {
            username = matcher.group(1);
            text = matcher.group(2);
        }
        Set<Map.Entry<String, ClientHandler>> set = getClientHandlerMap().entrySet();
        for (Map.Entry<String, ClientHandler> client : set) {
            if (!username.equals(client.getKey())) {
                ClientHandler clientHandler=  client.getValue();
                clientHandler.sendMessage(message);

            }
        }
        Date date = new Date();
        SimpleDateFormat format;
        format = new SimpleDateFormat(
                "dd.MM.yyyy hh:mm");

        dbHandler.adddHistory(username, format.format(date), text);
    }

    public Map<String, ClientHandler> getClientHandlerMap() {
        return clientHandlerMap;
    }

    public void sendMessageTo(String message) {
        String userTo = null;
        String userFrom = null;
        Matcher matcher = SENDTO_PATTERN.matcher(message);
        if (matcher.matches()) {
            userTo = matcher.group(1);
            userFrom = matcher.group(2);
        }
        if (!userTo.equals(userFrom)) {
            clientHandlerMap.get(userTo).sendMessage(message);
        } else return;
    }

    public void unsubscribeClient(String username) {
        clientHandlerMap.remove(username);
        broadcastUserDisconnected(username);
    }

    public void broadcastUserDisconnected(String userName) {
        getClientHandlerMap().remove(userName);
        Set<Map.Entry<String, ClientHandler>> set = getClientHandlerMap().entrySet();
        for (Map.Entry<String, ClientHandler> client : set) {
            client.getValue().sendMessage("/rmvuser " + userName + " отключился");
        }

    }

    public void broadcastUserConnected(String userName) {
        Set<Map.Entry<String, ClientHandler>> set = getClientHandlerMap().entrySet();
        for (Map.Entry<String, ClientHandler> client : set) {
            if (!userName.equals(client.getKey())) {
                client.getValue().sendMessage("/conectuser " + userName + " подключился");

            }
        }
    }

    public String[] getListUsers() {
        Collection<String> users = getClientHandlerMap().keySet();
        return users.toArray(new String[users.size()]);

    }

    public void changePassword(String message) throws SQLException {
        String userName = null;
        String password = null;
        Matcher matcher = CHANGEPWD_PATTERN.matcher(message);
        if (matcher.matches()) {
            userName = matcher.group(1);
            password = matcher.group(2);
        }
        dbHandler.changePassword(userName, password);
        getClientHandlerMap().get(userName).sendMessage("/pwdsucs");

    }

    public void sendHisory(String message) throws IOException {
        String userName = null;
        Matcher matcher = HISTORY_PATTERN.matcher(message);
        if (matcher.matches()) {
            userName = matcher.group(1);
        }
        getClientHandlerMap().get(userName).sendHistory(dbHandler.selectHisory());

    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}



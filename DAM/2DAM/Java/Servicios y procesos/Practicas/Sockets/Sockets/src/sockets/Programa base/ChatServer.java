package sockets;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 5000;
    // Set to keep track of all client handlers
    private static Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {
        System.out.println("Chat Server started on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                clients.add(handler);
                handler.start();
            }
        } catch (IOException e) {
            System.err.println("Error starting the server: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String nickname;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Prime the connection: first message should be the nickname
                this.nickname = in.readLine();
                if (this.nickname == null)
                    return;

                System.out.println(nickname + " joined the chat.");
                broadcast(nickname + " joined the chat.", this);

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("bye")) {
                        break;
                    }
                    System.out.println(nickname + ": " + message);
                    broadcast(nickname + ": " + message, this);
                }
            } catch (IOException e) {
                System.out.println("Error with client " + nickname + ": " + e.getMessage());
            } finally {
                clients.remove(this);
                broadcast(nickname + " left the chat.", this);
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
                System.out.println(nickname + " disconnected.");
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }

    // Send a message to all connected clients EXCEPT the sender
    private static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
}

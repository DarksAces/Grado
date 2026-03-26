package sockets.ejercicio1_chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 9000;
    private static Set<PrintWriter> clientWriters = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("[DGB] Chat Server is listening on port " + PORT);
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[DGB] New client connected: " + clientSocket.getInetAddress().getHostAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException ex) {
            System.err.println("[DGB] Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                clientWriters.add(out);
                
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("bye") || message.equalsIgnoreCase("exit")) {
                        out.println("[DGB] Disconnecting...");
                        break;
                    }
                    System.out.println("[DGB] [Client " + socket.getInetAddress() + "]: " + message);
                    broadcastMessage("[DGB] Client " + socket.getInetAddress() + ": " + message, out);
                }
            } catch (IOException e) {
                System.out.println("[DGB] Error handling client: " + e.getMessage());
            } finally {
                if (out != null) {
                    clientWriters.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("[DGB] Client disconnected.");
            }
        }

        private void broadcastMessage(String message, PrintWriter excludeWriter) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    if (writer != excludeWriter) {
                        writer.println(message);
                    }
                }
            }
        }
    }
}

package socket_dzf.ejercicio1_chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 9000;
    private static Set<PrintWriter> clientWriters = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("[DZF] Chat Server is listening on port " + PORT);
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[DZF] New client connected: " + clientSocket.getInetAddress().getHostAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException ex) {
            System.err.println("[DZF] Server exception: " + ex.getMessage());
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
                    System.out.println("[DZF] [Client " + socket.getInetAddress() + "]: " + message);
                    broadcastMessage("[DZF] Client " + socket.getInetAddress() + ": " + message, out);
                }
            } catch (IOException e) {
                System.out.println("[DZF] Error handling client: " + e.getMessage());
            } finally {
                if (out != null) {
                    clientWriters.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("[DZF] Client disconnected.");
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

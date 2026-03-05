package sockets;

import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server started on port " + port);
            System.out.println("Waiting for a client...");

            try (Socket socket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

                System.out.println("Client connected!");
                String clientMessage;
                
                // Thread to read messages from the client
                Thread readThread = new Thread(() -> {
                    try {
                        String msg;
                        while ((msg = in.readLine()) != null) {
                            System.out.println("Client: " + msg);
                            if (msg.equalsIgnoreCase("bye")) {
                                System.out.println("Client disconnected.");
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Connection closed.");
                    }
                });
                readThread.start();

                // Main thread to send messages to the client
                String serverMessage;
                while ((serverMessage = stdIn.readLine()) != null) {
                    out.println(serverMessage);
                    if (serverMessage.equalsIgnoreCase("bye")) {
                        System.out.println("Closing server...");
                        break;
                    }
                }
            }
            System.out.println("Server shut down orderly.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

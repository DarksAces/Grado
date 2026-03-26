package sockets.ejercicio1_chat;

import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String HOST = "localhost";
    private static final int PORT = 9000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)) {
            System.out.println("[DZF] Connected to the chat server.");
            System.out.println("[DZF] Type 'quit' or 'exit' to gracefully disconnect.");

            Thread readerThread = new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String response;
                    while (!socket.isClosed() && (response = in.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException ex) {
                    if (!socket.isClosed()) {
                        System.out.println("[DZF] Connection to server lost or closed.");
                    }
                }
            });
            readerThread.start();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            
            String text;
            while (true) {
                text = consoleReader.readLine();
                if (text == null || "quit".equalsIgnoreCase(text.trim()) || "exit".equalsIgnoreCase(text.trim())) {
                    System.out.println("[DZF] Exiting chat...");
                    break;
                }
                out.println(text);
            }
            
            socket.close();
            
        } catch (UnknownHostException ex) {
            System.err.println("[DZF] Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("[DZF] I/O Error: " + ex.getMessage());
        }
    }
}

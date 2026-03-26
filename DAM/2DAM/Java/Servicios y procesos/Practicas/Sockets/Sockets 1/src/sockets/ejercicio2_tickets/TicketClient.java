package sockets.ejercicio2_tickets;

import java.io.*;
import java.net.*;

public class TicketClient {
    private static final String HOST = "localhost";
    private static final int PORT = 9001;

    public static void main(String[] args) {
        System.out.println("[DGB] === Ticket Client ===");
        System.out.println("[DGB] Connecting to server on port " + PORT + "...");
        
        try (Socket socket = new Socket(HOST, PORT)) {
            System.out.println("[DGB] Connected!");
            System.out.println("[DGB] Type 'Dame ticket' to get a ticket or 'quit' to exit.");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            
            String text;
            while ((text = consoleReader.readLine()) != null) {
                if ("quit".equalsIgnoreCase(text.trim()) || "exit".equalsIgnoreCase(text.trim())) {
                    System.out.println("[DGB] Exiting...");
                    break;
                }
                
                out.println(text);
                
                String response = in.readLine();
                if (response == null) {
                    System.out.println("[DGB] Server disconnected.");
                    break;
                }
                
                System.out.println("[DGB] Server says: " + response);
            }
        } catch (IOException e) {
            System.err.println("[DGB] Communication error: " + e.getMessage());
        }
    }
}

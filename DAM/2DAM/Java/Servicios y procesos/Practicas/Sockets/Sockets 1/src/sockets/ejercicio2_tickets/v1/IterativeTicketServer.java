package sockets.ejercicio2_tickets.v1;

import java.io.*;
import java.net.*;

public class IterativeTicketServer {
    private static final int PORT = 9001;
    private static int ticketCounter = 1;

    public static void main(String[] args) {
        System.out.println("[DGB] === Iterative Ticket Server (V1) ===");
        System.out.println("[DGB] Listening on port " + PORT);
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                System.out.println("[DGB] Waiting for next client...");
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("[DGB] Client connected: " + clientSocket.getInetAddress());
                    
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    
                    String request = in.readLine();
                    
                    if (request != null && request.equalsIgnoreCase("Dame ticket")) {
                        out.println("Ticket #" + ticketCounter++);
                    } else {
                        out.println("Unknown command.");
                    }
                    
                    System.out.println("[DGB] Finished serving client. Closing connection.");
                } catch (IOException e) {
                    System.err.println("[DGB] Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("[DGB] Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

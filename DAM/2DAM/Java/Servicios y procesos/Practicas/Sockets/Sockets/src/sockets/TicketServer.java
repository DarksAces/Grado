package sockets;

import java.io.*;
import java.net.*;

public class TicketServer {
    public static void main(String[] args) {
        int port = 6000;
        int ticketNumber = 1;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Ticket Server (Single-threaded) started on port " + port);
            
            while (true) {
                System.out.println("Waiting for next client...");
                // This is the blocking call. But once accepted, we process it.
                // If we don't finish processing, we can't accept the next one.
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                    
                    // Simulate a slow process or waiting for a specific command
                    // If a user connects via Telnet and stays connected, this block keeps running
                    // and the server cannot go back to serverSocket.accept()
                    out.println("Welcome to Ticket Server. Type 'TICKET' to get your number.");
                    
                    String request = in.readLine(); // Blocking read
                    if (request != null && request.equalsIgnoreCase("TICKET")) {
                        out.println("Your ticket number is: " + (ticketNumber++));
                    } else {
                        out.println("Invalid request. Closing connection.");
                    }
                    
                    System.out.println("Request handled. Closing client connection.");
                } catch (IOException e) {
                    System.out.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

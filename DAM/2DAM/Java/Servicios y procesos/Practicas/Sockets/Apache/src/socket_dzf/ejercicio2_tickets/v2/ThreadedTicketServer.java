package socket_dzf.ejercicio2_tickets.v2;

import java.io.*;
import java.net.*;

public class ThreadedTicketServer {
    private static final int PORT = 9001;
    private static int ticketCounter = 1;

    public static void main(String[] args) {
        System.out.println("[DZF] === Threaded Ticket Server (V2) ===");
        System.out.println("[DZF] Listening on port " + PORT);
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[DZF] Client connected: " + clientSocket.getInetAddress());
                
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("[DZF] Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String request = in.readLine();
            
            if (request != null && request.equalsIgnoreCase("Dame ticket")) {
                out.println("Ticket #" + ticketCounter++);
            } else {
                out.println("Unknown command.");
            }
            
            System.out.println("[DZF] Finished serving client. Closing connection.");
        } catch (IOException e) {
            System.err.println("[DZF] Error handling client: " + e.getMessage());
        } finally {
            try {
                if (!clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
            }
        }
    }
}

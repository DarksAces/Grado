package socket_dzf.ejercicio2_tickets.v3;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ThreadPoolTicketServer {
    private static final int PORT = 9001;
    private static final int MAX_THREADS = 10;
    private static int ticketCounter = 1;

    public static void main(String[] args) {
        System.out.println("[DZF] === Thread Pool Ticket Server (V3) ===");
        System.out.println("[DZF] Listening on port " + PORT + " with max " + MAX_THREADS + " threads.");
        
        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[DZF] Client connected: " + clientSocket.getInetAddress() + " - Dispatching to pool.");
                
                threadPool.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("[DZF] Server error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
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

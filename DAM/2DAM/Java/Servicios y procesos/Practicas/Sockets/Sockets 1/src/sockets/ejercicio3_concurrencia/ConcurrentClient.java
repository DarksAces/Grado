package sockets.ejercicio3_concurrencia;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ConcurrentClient {
    private static final String HOST = "localhost";
    private static final int PORT = 9002;
    private static final int NUM_CLIENTS = 2000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("[DGB] === Concurrent Ticket Client ===");
        System.out.println("[DGB] Requesting " + NUM_CLIENTS + " tickets concurrently...");
        
        ExecutorService executor = Executors.newFixedThreadPool(500);
        List<Callable<String>> tasks = new ArrayList<>();
        
        for (int i = 0; i < NUM_CLIENTS; i++) {
            tasks.add(() -> {
                try (Socket socket = new Socket(HOST, PORT)) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    
                    out.println("Dame ticket");
                    return in.readLine(); 
                } catch (IOException e) {
                    return "Error: " + e.getMessage();
                }
            });
        }
        
        List<Future<String>> results = executor.invokeAll(tasks);
        executor.shutdown();
        
        Set<String> tickets = new HashSet<>();
        int duplicates = 0;
        
        for (Future<String> f : results) {
            try {
                String ticket = f.get();
                if (ticket != null && ticket.startsWith("Ticket #")) {
                    if (!tickets.add(ticket)) {
                        System.out.println("[DGB] DUPLICATE DETECTED: " + ticket);
                        duplicates++;
                    }
                }
            } catch (ExecutionException e) {
            }
        }
        
        System.out.println("[DGB] =========================================");
        System.out.println("[DGB] Finished.");
        System.out.println("[DGB] Total requests sent: " + NUM_CLIENTS);
        System.out.println("[DGB] Unique tickets received: " + tickets.size());
        System.out.println("[DGB] Total DUPLICATED tickets: " + duplicates);
        
        if (duplicates > 0) {
            System.err.println("[DGB] WARNING: Race condition detected!");
        } else {
            System.out.println("[DGB] SUCCESS: No duplicates found!");
        }
    }
}

package sockets.ejercicio3_concurrencia;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicServer {
    private static final int PORT = 9002;
    private static final int MAX_THREADS = 100;
    
    private static AtomicInteger ticketCounter = new AtomicInteger(1);

    public static void main(String[] args) {
        System.out.println("[DGB] === Atomic Ticket Server ===");
        System.out.println("[DGB] Listening on port " + PORT);
        
        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
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
            
            if ("Dame ticket".equalsIgnoreCase(request)) {
                int assignedTicket = ticketCounter.getAndIncrement();
                try { Thread.sleep(2); } catch (InterruptedException e) {}
                out.println("Ticket #" + assignedTicket);
            } else {
                out.println("Unknown command.");
            }
        } catch (IOException e) {
        } finally {
            try { clientSocket.close(); } catch (IOException e) { }
        }
    }
}

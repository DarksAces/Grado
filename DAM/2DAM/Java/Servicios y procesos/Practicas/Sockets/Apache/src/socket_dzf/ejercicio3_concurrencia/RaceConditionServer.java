package socket_dzf.ejercicio3_concurrencia;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class RaceConditionServer {
    private static final int PORT = 9002;
    private static final int MAX_THREADS = 100;
    
    // Intentionally NOT thread-safe
    private static int ticketCounter = 1;

    public static void main(String[] args) {
        System.out.println("[DZF] === Race Condition Server ===");
        System.out.println("[DZF] Listening on port " + PORT);
        
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
                int currentTicket = ticketCounter;
                try { Thread.sleep(2); } catch (InterruptedException e) {}
                ticketCounter = currentTicket + 1;
                
                out.println("Ticket #" + currentTicket);
            } else {
                out.println("Unknown command.");
            }
        } catch (IOException e) {
        } finally {
            try { clientSocket.close(); } catch (IOException e) { }
        }
    }
}

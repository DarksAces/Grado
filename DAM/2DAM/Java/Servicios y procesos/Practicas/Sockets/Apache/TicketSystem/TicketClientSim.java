import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Ticket Client Simulator
 * Launches multiple threads to request tickets simultaneously.
 * Detects if any duplicate ticket ID is received.
 */
public class TicketClientSim {
    private static final int NUM_CLIENTS = 50;
    private static final String HOST = "localhost";
    private static final int PORT = 6000;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_CLIENTS);
        Set<Integer> receivedTickets = Collections.synchronizedSet(new HashSet<>());
        List<Integer> duplicates = Collections.synchronizedList(new ArrayList<>());

        System.out.println("Starting Ticket Client Simulation with " + NUM_CLIENTS + " clients...");

        for (int i = 0; i < NUM_CLIENTS; i++) {
            executor.execute(() -> {
                try (Socket socket = new Socket(HOST, PORT);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    
                    String line = in.readLine();
                    if (line != null && line.contains("Your Ticket ID: ")) {
                        int ticketId = Integer.parseInt(line.substring(line.lastIndexOf(": ") + 2).trim());
                        
                        if (!receivedTickets.add(ticketId)) {
                            System.err.println("DUPLICATE DETECTED: " + ticketId);
                            duplicates.add(ticketId);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Connection error: " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        try {
            if (executor.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("\nSimulation Finished.");
                System.out.println("Total tickets received: " + receivedTickets.size());
                System.out.println("Total duplicates detected: " + duplicates.size());
                if (duplicates.isEmpty()) {
                    System.out.println("SUCCESS: No duplicates found!");
                } else {
                    System.out.println("FAILURE: Duplicates were found: " + duplicates);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

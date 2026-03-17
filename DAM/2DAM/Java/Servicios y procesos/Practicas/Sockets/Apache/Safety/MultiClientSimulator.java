import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Multi-Client Simulator
 * Launches many simultaneous requests to detect duplicate tickets.
 */
public class MultiClientSimulator {
    public static void main(String[] args) {
        int numClients = 100;
        String hostname = "localhost";
        int port = 6000;
        
        Set<Integer> ticketsReceived = Collections.synchronizedSet(new HashSet<>());
        List<Integer> duplicates = Collections.synchronizedList(new ArrayList<>());
        
        ExecutorService executor = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(numClients);

        System.out.println("Starting simulator with " + numClients + " clients...");

        for (int i = 0; i < numClients; i++) {
            executor.execute(() -> {
                try (Socket socket = new Socket(hostname, port);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    
                    String response = in.readLine();
                    if (response != null) {
                        int ticketId = Integer.parseInt(response.trim());
                        if (!ticketsReceived.add(ticketId)) {
                            duplicates.add(ticketId);
                            System.err.println("DUPLICATE FOUND: " + ticketId);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Connection error: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        
        System.out.println("\nSimulation Finished.");
        System.out.println("Total tickets processed: " + ticketsReceived.size());
        System.out.println("Total duplicates found: " + duplicates.size());
        
        if (duplicates.isEmpty()) {
            System.out.println("TEST PASSED: No duplicates detected.");
        } else {
            System.out.println("TEST FAILED: Duplicates were found!");
        }
    }
}

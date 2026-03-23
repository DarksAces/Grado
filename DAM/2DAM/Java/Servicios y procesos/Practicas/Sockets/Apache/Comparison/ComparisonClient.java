import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * Comparison Client
 * Tests how the different simulation servers handle rapid requests.
 */
public class ComparisonClient {
    private static final int PORT = 7000;
    private static final int REQUESTS = 20;

    public static void main(String[] args) {
        System.out.println("Testing on port " + PORT + "...");
        long startTime = System.currentTimeMillis();
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < REQUESTS; i++) {
            final int id = i;
            executor.execute(() -> {
                try (Socket socket = new Socket("localhost", PORT);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String resp = in.readLine();
                    System.out.println("Req " + id + " -> " + resp);
                } catch (IOException e) {
                    System.err.println("Req " + id + " failed: " + e.getMessage());
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("\nTotal time for " + REQUESTS + " requests: " + (endTime - startTime) + "ms");
    }
}

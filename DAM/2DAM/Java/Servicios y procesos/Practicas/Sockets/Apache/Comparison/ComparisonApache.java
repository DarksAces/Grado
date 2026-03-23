import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * Comparison Apache Simulation
 * Uses a Thread Pool to handle multiple clients concurrently (Thread-per-request).
 */
public class ComparisonApache {
    private static final int PORT = 7000;
    private static final int THREAD_POOL_SIZE = 5; // Restricted pool to show queueing

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Apache-like Server (Thread Pool) started on port " + PORT);
            System.out.println("Pool size: " + THREAD_POOL_SIZE);

            while (true) {
                Socket socket = serverSocket.accept();
                pool.execute(() -> {
                    try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                        // Simulate blocking process (e.g. PHP script, DB query)
                        Thread.sleep(500); 
                        out.println("HTTP/1.1 200 OK (Processed by Apache Thread)");
                        socket.close();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}

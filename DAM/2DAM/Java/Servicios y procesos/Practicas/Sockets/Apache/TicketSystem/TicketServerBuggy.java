import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * Ticket Server Solution 3: Race Condition Demo
 * This server uses a thread pool but NO synchronization.
 * This will cause duplicate tickets to be issued under heavy load.
 */
public class TicketServerBuggy {
    private static int ticketCounter = 3000;
    private static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args) {
        int port = 6000;
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Buggy Ticket Server (No Sync) started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                pool.execute(() -> {
                    try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                        // Small delay to increase the chance of race conditions
                        Thread.sleep(10); 
                        
                        int ticketId = ticketCounter++; // NO SYNCHRONIZATION!
                        
                        out.println("Your Ticket ID: " + ticketId);
                        socket.close();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

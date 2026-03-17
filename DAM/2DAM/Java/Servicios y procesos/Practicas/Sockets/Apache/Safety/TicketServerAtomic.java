import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Ticket Server with AtomicInteger
 * Fixes the race condition using AtomicInteger for better performance.
 */
public class TicketServerAtomic {
    private static AtomicInteger ticketCounter = new AtomicInteger(7000);

    public static void main(String[] args) {
        int port = 6000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Ticket Server (Atomic) started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                        // incrementAndGet is atomic
                        int ticketId = ticketCounter.incrementAndGet();
                        
                        out.println(ticketId);
                        socket.close();
                    } catch (Exception e) {
                        // Ignore
                    }
                }).start();
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }
}

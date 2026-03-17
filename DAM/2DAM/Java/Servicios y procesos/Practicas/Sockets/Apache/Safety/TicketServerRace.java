import java.io.*;
import java.net.*;

/**
 * Ticket Server with Race Condition
 * This server is purposefully NOT thread-safe to demonstrate how
 * multiple clients can end up with the same ticket ID.
 */
public class TicketServerRace {
    private static int ticketCounter = 5000;

    public static void main(String[] args) {
        int port = 6000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Ticket Server (Race Condition) started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                        // Small delay to increase the chance of race conditions
                        Thread.sleep(10);
                        
                        // NOT synchronized
                        int ticketId = ticketCounter++; 
                        
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

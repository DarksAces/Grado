import java.io.*;
import java.net.*;

/**
 * Ticket Server with Synchronized
 * Fixes the race condition using a synchronized block.
 */
public class TicketServerSync {
    private static int ticketCounter = 6000;

    public static void main(String[] args) {
        int port = 6000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Ticket Server (Synchronized) started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                        int ticketId;
                        
                        // Using synchronized to ensure atomicity
                        synchronized (TicketServerSync.class) {
                            ticketId = ticketCounter++;
                        }
                        
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

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Atomic Ticket Server
 * Uses AtomicInteger to prevent duplicate tickets efficiently without explicit locks.
 */
public class AtomicTicketServer {
    private static AtomicInteger ticketCounter = new AtomicInteger(5000);
    private static final int PORT = 6000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Atomic Ticket Server started on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                new TicketHandler(socket).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static class TicketHandler extends Thread {
        private Socket socket;

        public TicketHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                // Simulate some work
                Thread.sleep(10); 

                // Atomic increment is thread-safe and lock-free
                int myTicket = ticketCounter.getAndIncrement();

                out.println("Your Ticket ID: " + myTicket);
                socket.close();
            } catch (IOException | InterruptedException e) {
                System.err.println("Handler error: " + e.getMessage());
            }
        }
    }
}

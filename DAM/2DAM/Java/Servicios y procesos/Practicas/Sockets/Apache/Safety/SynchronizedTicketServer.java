import java.io.*;
import java.net.*;

/**
 * Synchronized Ticket Server
 * Uses synchronized blocks to prevent duplicate tickets in a multi-threaded environment.
 */
public class SynchronizedTicketServer {
    private static int ticketCounter = 4000;
    private static final int PORT = 6000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Synchronized Ticket Server started on port " + PORT);

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

                int myTicket;
                synchronized (SynchronizedTicketServer.class) {
                    myTicket = ticketCounter++;
                }

                out.println("Your Ticket ID: " + myTicket);
                socket.close();
            } catch (IOException | InterruptedException e) {
                System.err.println("Handler error: " + e.getMessage());
            }
        }
    }
}

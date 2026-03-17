import java.io.*;
import java.net.*;

/**
 * Ticket Server Solution 2: Concurrency with Threads
 * This server creates a new thread for each client connection.
 */
public class TicketServerV2 {
    private static int ticketCounter = 2000;

    public static void main(String[] args) {
        int port = 6000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Ticket Server V2 (Thread-per-request) started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress());
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                
                // Simulate processing time
                Thread.sleep(2000); 
                
                int ticketId;
                synchronized (TicketServerV2.class) {
                    ticketId = ticketCounter++;
                }
                out.println("Your Ticket ID: " + ticketId);
                System.out.println("Issued ticket: " + ticketId + " to " + socket.getInetAddress());
                
                socket.close();
            } catch (IOException | InterruptedException e) {
                System.err.println("Error handling client: " + e.getMessage());
            }
        }
    }
}

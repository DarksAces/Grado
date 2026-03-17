import java.io.*;
import java.net.*;

/**
 * Ticket Server Solutions 1: Iterative (Single-threaded)
 * This server handles one client at a time. 
 * If a connection is occupied (e.g. by telnet), other clients will be blocked.
 */
public class TicketServerV1 {
    private static int ticketCounter = 1000;

    public static void main(String[] args) {
        int port = 6000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Ticket Server V1 (Iterative) started on port " + port);

            while (true) {
                System.out.println("Waiting for client...");
                try (Socket socket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    
                    System.out.println("Client connected: " + socket.getInetAddress());
                    
                    // Simulate processing time
                    Thread.sleep(2000); 
                    
                    int ticketId = ticketCounter++;
                    out.println("Your Ticket ID: " + ticketId);
                    System.out.println("Issued ticket: " + ticketId);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (IOException e) {
                    System.err.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }
}

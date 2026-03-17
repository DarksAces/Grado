import java.io.*;
import java.net.*;

/**
 * Basic Ticket Client
 * Connects to the server and receives a ticket ID.
 */
public class TicketClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 6000;

        try (Socket socket = new Socket(hostname, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            System.out.println("Connected to Ticket Server...");
            String response = in.readLine();
            System.out.println("Server Response: " + response);
            
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}

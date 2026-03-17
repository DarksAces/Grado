import java.io.*;
import java.net.*;

/**
 * Comparison Client
 * Connects to both servers to see the difference in behavior.
 */
public class ComparisonClient {
    public static void main(String[] args) {
        System.out.println("--- Testing Apache Model ---");
        testServer(9001);
        
        System.out.println("\n--- Testing Nginx Model ---");
        testServer(9002);
    }

    private static void testServer(int port) {
        try (Socket socket = new Socket("localhost", port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            out.println("Hello");
            System.out.println("Server " + port + ": " + in.readLine());
            
        } catch (IOException e) {
            System.err.println("Could not connect to port " + port);
        }
    }
}

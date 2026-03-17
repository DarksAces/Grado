import java.io.*;
import java.net.*;

/**
 * Basic Chat Client
 * Supports multiple messages and an ordered shutdown.
 */
public class ClientChat {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(hostname, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to Chat Server!");
            System.out.println("Type your messages (type 'quit' to exit):");

            String serverMessage;
            
            // Thread to read from console and send to server
            Thread sendThread = new Thread(() -> {
                try {
                    String clientInput;
                    while ((clientInput = consoleIn.readLine()) != null) {
                        out.println(clientInput);
                        if (clientInput.equalsIgnoreCase("quit")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error sending message: " + e.getMessage());
                }
            });
            sendThread.start();

            // Main thread reads from server
            while ((serverMessage = in.readLine()) != null) {
                System.out.println("Server: " + serverMessage);
                if (serverMessage.equalsIgnoreCase("quit")) {
                    System.out.println("Server requested shutdown.");
                    break;
                }
            }

            System.out.println("Closing connection...");
        } catch (UnknownHostException e) {
            System.err.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
        System.out.println("Client closed.");
    }
}

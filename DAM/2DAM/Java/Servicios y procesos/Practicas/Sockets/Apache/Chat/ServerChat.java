import java.io.*;
import java.net.*;

/**
 * Basic Chat Server
 * Supports multiple messages and an ordered shutdown.
 */
public class ServerChat {
    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server started on port " + port);
            System.out.println("Waiting for a client...");

            try (Socket socket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in))) {

                System.out.println("Client connected!");
                String clientMessage;
                
                // Thread to read from console and send to client
                Thread sendThread = new Thread(() -> {
                    try {
                        String serverInput;
                        while ((serverInput = consoleIn.readLine()) != null) {
                            out.println(serverInput);
                            if (serverInput.equalsIgnoreCase("quit")) {
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error sending message: " + e.getMessage());
                    }
                });
                sendThread.start();

                // Main thread reads from client
                while ((clientMessage = in.readLine()) != null) {
                    System.out.println("Client: " + clientMessage);
                    if (clientMessage.equalsIgnoreCase("quit")) {
                        System.out.println("Client requested shutdown.");
                        break;
                    }
                }
                
                System.out.println("Closing connection...");
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
        System.out.println("Server shut down.");
    }
}

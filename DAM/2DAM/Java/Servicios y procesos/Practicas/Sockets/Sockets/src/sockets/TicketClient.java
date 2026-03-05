package sockets;

import java.io.*;
import java.net.*;

public class TicketClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 6000;

        try (Socket socket = new Socket(host, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to Ticket Server.");

            // Read welcome message
            System.out.println("Server says: " + in.readLine());

            // Request ticket
            System.out.println("Requesting ticket...");
            out.println("TICKET");

            // Read ticket
            String response = in.readLine();
            System.out.println("Response: " + response);

        } catch (IOException e) {
            System.err.println("Could not connect to server. It might be occupied! " + e.getMessage());
        }
    }
}

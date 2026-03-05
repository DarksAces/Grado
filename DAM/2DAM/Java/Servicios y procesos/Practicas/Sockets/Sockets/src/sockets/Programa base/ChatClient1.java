package sockets;

import java.io.*;
import java.net.*;

public class ChatClient1 {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(hostname, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("--- Client 1 ---");
            System.out.print("Enter your nickname: ");
            String nickname = stdIn.readLine();
            out.println(nickname); // Send nickname as first message

            System.out.println("Connected! Type your messages (type 'bye' to quit):");

            // Thread to read messages from the server
            Thread readThread = new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = in.readLine()) != null) {
                        System.out.println("\n" + serverMsg);
                        if (serverMsg.equalsIgnoreCase("bye")) {
                            System.out.println("Server ended the chat. Press Enter to exit.");
                            break;
                        }
                        System.out.print("> "); // Re-print prompt
                    }
                } catch (IOException e) {
                    System.out.println("\nConnection to server lost.");
                }
            });
            readThread.setDaemon(true);
            readThread.start();

            // Main thread to send messages to the server
            String userInput;
            System.out.print("> ");
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                if (userInput.equalsIgnoreCase("bye")) {
                    break;
                }
                System.out.print("> ");
            }

            System.out.println("Closing client 1...");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostname);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostname);
        }
    }
}

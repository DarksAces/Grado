import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * Ticket Server Solution 3: Thread Pool
 * This server uses an ExecutorService (Thread Pool) to manage client connections.
 */
public class TicketServerV3 {
    private static int ticketCounter = 3000;
    private static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args) {
        int port = 6000;
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Ticket Server V3 (Thread Pool) started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress());
                pool.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                
                // Simulate processing time
                Thread.sleep(2000); 
                
                int ticketId;
                synchronized (TicketServerV3.class) {
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

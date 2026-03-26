package socket_dzf.ejercicio4_simulacion;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ApacheSimulator {
    private static final int PORT = 8080;
    private static AtomicInteger activeThreads = new AtomicInteger(0);

    public static void main(String[] args) {
        System.out.println("[DZF] === Apache Simulator (Thread-per-Connection) ===");
        System.out.println("[DZF] Listening on port " + PORT + " using blocking I/O...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleConnection(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket socket) {
        int threads = activeThreads.incrementAndGet();
        if (threads % 100 == 0) System.out.println("[DZF] Active Threads (Connections): " + threads);
        
        try (
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream()
        ) {
            int data;
            while ((data = in.read()) != -1) {
            }

            String response = "HTTP/1.1 200 OK\r\nConnection: close\r\n\r\n[DZF] Hello from Apache Simulator!";
            out.write(response.getBytes());
            out.flush();

        } catch (IOException e) {
        } finally {
            activeThreads.decrementAndGet();
            try { socket.close(); } catch (IOException e) {}
        }
    }
}

package sockets.ejercicio4_simulacion;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class HeavyLoadClient {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final int NUM_CONNECTIONS = 3000;
    
    private static final List<Socket> openSockets = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws InterruptedException {
        System.out.println("[DZF] === Heavy Load Generator ===");
        System.out.println("[DZF] Will attempt to open " + NUM_CONNECTIONS + " concurrent connections to port " + PORT);
        
        ExecutorService executor = Executors.newFixedThreadPool(100);
        
        for (int i = 0; i < NUM_CONNECTIONS; i++) {
            final int id = i;
            executor.submit(() -> {
                try {
                    Socket socket = new Socket(HOST, PORT);
                    socket.setKeepAlive(true);
                    openSockets.add(socket);
                    
                    if (id % 100 == 0) {
                        System.out.println("[DZF] Successfully opened " + openSockets.size() + " connections...");
                    }
                    
                    OutputStream out = socket.getOutputStream();
                    out.write(("GET / HTTP/1.1\r\nHost: localhost\r\n").getBytes());
                    out.flush();
                    
                    while (true) {
                        Thread.sleep(10000);
                    }
                } catch (Exception e) {
                    System.err.println("[DZF] Failed to connect or send data on connection #" + id + ": " + e.getMessage());
                }
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }
}

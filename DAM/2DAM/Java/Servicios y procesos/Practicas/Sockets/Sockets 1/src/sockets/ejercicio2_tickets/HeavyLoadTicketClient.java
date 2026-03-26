package sockets.ejercicio2_tickets;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * CLIENTE DE ALTA CARGA (HEAVY LOAD CLIENT)
 * Simula múltiples peticiones simultáneas para detectar condiciones de carrera (Race Conditions).
 */
public class HeavyLoadTicketClient {
    private static final String HOST = "localhost";
    private static final int PORT = 9001;
    private static final int TOTAL_REQUESTS = 100;

    public static void main(String[] args) {
        System.out.println("[DZF] === Simulación de Alta Carga de Tickets ===");
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Set<String> receivedTickets = Collections.synchronizedSet(new HashSet<>());
        List<String> duplicates = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < TOTAL_REQUESTS; i++) {
             // Submitting i to effectively move it to the final effectively final lambda context
            final int requestId = i;
            executor.submit(() -> {
                try (Socket socket = new Socket(HOST, PORT);
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    
                    out.println("Dame ticket");
                    String ticket = in.readLine();
                    
                    if (ticket != null) {
                        synchronized(receivedTickets) {
                            if (receivedTickets.contains(ticket)) {
                                System.err.println("[DZF] [ERROR] ¡TICKET DUPLICADO DETECTADO!: " + ticket);
                                duplicates.add(ticket);
                            } else {
                                receivedTickets.add(ticket);
                            }
                        }
                    }
                } catch (IOException e) {
                    // Fail silently or log error
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
            System.out.println("\n[DZF] === RESULTADOS DE LA SIMULACIÓN ===");
            System.out.println("[DZF] Peticiones totales: " + TOTAL_REQUESTS);
            System.out.println("[DZF] Tickets únicos recibidos: " + receivedTickets.size());
            System.out.println("[DZF] Duplicados detectados: " + duplicates.size());
            
            if (duplicates.size() > 0) {
                System.out.println("[DZF] ¡Se ha comprobado la vulnerabilidad a condiciones de carrera!");
            } else {
                System.out.println("[DZF] No se detectaron duplicados.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

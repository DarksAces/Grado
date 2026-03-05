import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Servidor {
    // Contador atomico para evitar condiciones de carrera sin synchronized
    private static AtomicInteger contador = new AtomicInteger(0);

    // Pool de hilos fijo para limitar el numero de clientes simultaneos
    // (Backpressure)
    // Solo 3 clientes pueden ser atendidos a la vez; el resto espera en la cola del
    // pool.
    private static final int MAX_HILOS = 3;
    private static ExecutorService pool = Executors.newFixedThreadPool(MAX_HILOS);

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(6000)) {
            System.out.println("Servidor con POOL DE HILOS (max: " + MAX_HILOS + ") encendido...");

            while (true) {
                Socket s = ss.accept();
                System.out.println("Nuevo cliente conectado. Enviando al pool...");

                // En lugar de crear un hilo nuevo, lo mandamos al pool
                pool.execute(() -> handle(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    private static void handle(Socket s) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {

            String msj = in.readLine();

            // Simulamos que el servidor tarda 2 segundos en procesar
            Thread.sleep(2000);

            if ("Dame ticket".equals(msj)) {
                // Generacion de ticket segura entre hilos
                int miTicket = contador.incrementAndGet();
                out.println("Ticket: " + miTicket);
                System.out.println("Ticket #" + miTicket + " entregado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                s.close();
            } catch (IOException e) {
            }
        }
    }
}

import java.io.*;
import java.net.*;

public class Servidor {
    private static int contador = 0;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(6000)) {
            System.out.println("Servidor CONCURRENTE (Hilos) encendido...");
            while (true) {
                Socket s = ss.accept();
                System.out.println("Nuevo cliente conectado. Lanzando hilo...");

                // Por cada cliente -> nuevo hilo
                new Thread(() -> handle(s)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handle(Socket s) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {

            String msj = in.readLine();

            // Simulamos que el servidor tarda 2 segundos en procesar
            Thread.sleep(2000);

            if ("Dame ticket".equals(msj)) {
                // Sincronizamos el acceso al contador para que no haya errores
                synchronized (Servidor.class) {
                    out.println("Ticket: " + (++contador));
                }
            }
            System.out.println("Cliente atendido en su hilo.");
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

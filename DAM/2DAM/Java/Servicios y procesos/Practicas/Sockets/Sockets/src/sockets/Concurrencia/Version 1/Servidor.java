import java.io.*;
import java.net.*;

public class Servidor {
    private static int contador = 0;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(6000)) {
            System.out.println("Servidor iterativo encendido...");
            while (true) {
                try (Socket s = ss.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {

                    System.out.println("Atendiendo cliente...");
                    String msj = in.readLine();

                    // Simulamos que el servidor tarda 2 segundos en procesar
                    Thread.sleep(2000);

                    if ("Dame ticket".equals(msj)) {
                        out.println("Ticket: " + (++contador));
                    }
                    System.out.println("Cliente terminado.");
                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.net.*;

public class Cliente2 {
    public static void main(String[] args) {
        try (Socket s = new Socket("localhost", 6000)) {
            System.out.println("Conectado. No envio nada por 10 seg...");
            Thread.sleep(10000);
        } catch (Exception e) {
        }
    }
}

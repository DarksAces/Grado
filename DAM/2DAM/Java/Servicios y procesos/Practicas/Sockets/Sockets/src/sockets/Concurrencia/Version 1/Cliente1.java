import java.io.*;
import java.net.*;

public class Cliente1 {
    public static void main(String[] args) {
        try (Socket s = new Socket("localhost", 6000);
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            out.println("Dame ticket");
            System.out.println("Recibido: " + in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

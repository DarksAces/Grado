import java.io.*;
import java.net.*;

/**
 * Apache-style Server Mock (Thread-per-connection)
 * Each connection is handled by a dedicated thread.
 * This is the classic blocking I/O model.
 */
public class ApacheModelServer {
    public static void main(String[] args) {
        int port = 9001;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Apache-style Server (Blocking I/O) on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handle(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handle(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            
            String line = in.readLine();
            log("Handling " + socket.getInetAddress() + " in thread " + Thread.currentThread().getName());
            Thread.sleep(1000); // Simulate work
            out.println("Response from Apache Thread-per-connection model");
            
        } catch (Exception e) {
            // Ignore
        }
    }

    private static void log(String msg) {
        System.out.println(System.currentTimeMillis() + " - " + msg);
    }
}

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

/**
 * Full Apache Server Simulation
 * Reads configuration, waits for HTTP requests, and serves files.
 */
public class FullApacheServer {
    private static int port = 8080;
    private static String documentRoot = "";

    public static void main(String[] args) {
        loadConfig("d:/Documentos/GitHub/Grado/DAM/2DAM/Java/Servicios y procesos/Practicas/Sockets/Apache/MockApache/apache_config.txt");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Apache Mock Server running on port " + port);
            System.out.println("Serving from: " + documentRoot);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new RequestHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadConfig(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Listen ")) {
                    port = Integer.parseInt(line.substring(7).trim());
                } else if (line.startsWith("DocumentRoot ")) {
                    documentRoot = line.substring(13).replace("\"", "").trim();
                }
            }
        } catch (IOException e) {
            System.err.println("Warning: Config file not found. Using defaults.");
        }
    }

    private static class RequestHandler extends Thread {
        private Socket socket;

        public RequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 OutputStream out = socket.getOutputStream()) {

                String requestLine = in.readLine();
                if (requestLine == null) return;
                
                System.out.println("Request: " + requestLine);
                String[] parts = requestLine.split(" ");
                if (parts.length < 2) return;

                String fileName = parts[1];
                if (fileName.equals("/")) fileName = "/index.html";

                Path filePath = Paths.get(documentRoot, fileName);
                
                if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                    byte[] content = Files.readAllBytes(filePath);
                    String header = "HTTP/1.1 200 OK\r\n" +
                                   "Content-Type: text/html\r\n" +
                                   "Content-Length: " + content.length + "\r\n" +
                                   "\r\n";
                    out.write(header.getBytes());
                    out.write(content);
                } else {
                    String error = "HTTP/1.1 404 Not Found\r\n\r\n<h1>404 File Not Found</h1>";
                    out.write(error.getBytes());
                }
                out.flush();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

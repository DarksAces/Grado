import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.nio.file.*;

/**
 * Mock Apache Server
 * Uses Sockets to simulate a real web server.
 * Loads configuration from a properties file and serves static content.
 */
public class MockApacheServer {
    private static Properties config = new Properties();
    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        loadConfig();
        int port = Integer.parseInt(config.getProperty("server.port", "8080"));

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Mock Apache Server started on port " + port);
            System.out.println("Document Root: " + config.getProperty("server.root"));

            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(() -> handleRequest(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static void loadConfig() {
        try (InputStream input = new FileInputStream("MockApache/config.properties")) {
            config.load(input);
        } catch (IOException ex) {
            System.err.println("Could not load config, using defaults.");
        }
    }

    private static void handleRequest(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             OutputStream out = socket.getOutputStream()) {

            String requestLine = in.readLine();
            if (requestLine == null) return;
            
            log("Request: " + requestLine);
            
            String[] tokens = requestLine.split(" ");
            if (tokens.length < 2) return;
            
            String path = tokens[1];
            if (path.equals("/")) {
                path = "/" + config.getProperty("server.defaultPage", "index.html");
            }

            File file = new File(config.getProperty("server.root", "./www") + path);
            if (file.exists() && !file.isDirectory()) {
                sendResponse(out, "200 OK", Files.probeContentType(file.toPath()), Files.readAllBytes(file.toPath()));
            } else {
                String errorMsg = "<h1>404 Not Found</h1>";
                sendResponse(out, "404 Not Found", "text/html", errorMsg.getBytes());
            }

        } catch (IOException e) {
            System.err.println("Request error: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException e) {}
        }
    }

    private static void sendResponse(OutputStream out, String status, String contentType, byte[] content) throws IOException {
        PrintWriter writer = new PrintWriter(out);
        writer.println("HTTP/1.1 " + status);
        writer.println("Content-Type: " + contentType);
        writer.println("Content-Length: " + content.length);
        writer.println("Connection: close");
        writer.println();
        writer.flush();
        out.write(content);
        out.flush();
    }

    private static synchronized void log(String message) {
        String logLine = new Date() + " - " + message;
        System.out.println(logLine);
        try (FileWriter fw = new FileWriter(config.getProperty("server.logFile", "server.log"), true)) {
            fw.write(logLine + "\n");
        } catch (IOException e) {
            System.err.println("Log error: " + e.getMessage());
        }
    }
}

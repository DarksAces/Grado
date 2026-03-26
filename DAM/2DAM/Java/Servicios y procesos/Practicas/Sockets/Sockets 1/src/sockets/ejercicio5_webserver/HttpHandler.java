package sockets.ejercicio5_webserver;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class HttpHandler implements Runnable {
    private Socket socket;
    private Config config;

    public HttpHandler(Socket socket, Config config) {
        this.socket = socket;
        this.config = config;
    }

    @Override
    public void run() {
        String clientIp = socket.getInetAddress().getHostAddress();
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream out = socket.getOutputStream()
        ) {
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) return;

            // Log access tentatively
            System.out.println("[DGB] Incoming: " + requestLine);
            
            // Parse headers to find "Host: "
            String hostHeader = "localhost";
            String line;
            while (!(line = in.readLine()).isEmpty()) {
                if (line.toLowerCase().startsWith("host:")) {
                    hostHeader = line.substring(5).trim();
                    // Remove port if present (e.g., site1.local:8080)
                    if (hostHeader.contains(":")) {
                        hostHeader = hostHeader.split(":")[0];
                    }
                }
            }

            // Determine VirtualHost
            String documentRoot = config.defaultDocumentRoot;
            for (VirtualHost vh : config.virtualHosts) {
                if (vh.getServerName().equalsIgnoreCase(hostHeader)) {
                    documentRoot = vh.getDocumentRoot();
                    break;
                }
            }
            
            // Map request to filesystem
            String[] reqParts = requestLine.split(" ");
            String method = reqParts[0];
            String path = reqParts[1];
            
            if (path.equals("/")) {
                path = "/index.html"; // default page
            }
            
            // Combine paths
            Path filePath = Paths.get(config.serverRoot, documentRoot, path);
            File file = filePath.toFile();
            
            if (file.exists() && !file.isDirectory()) {
                sendResponse(out, 200, "OK", "text/html", Files.readAllBytes(filePath));
                Logger.logAccess(clientIp, requestLine, 200);
            } else {
                sendResponse(out, 404, "Not Found", "text/html", "<h1>404 Not Found</h1>".getBytes());
                Logger.logAccess(clientIp, requestLine, 404);
            }

        } catch (Exception e) {
            Logger.logError("Error handling request from " + clientIp + ": " + e.getMessage());
            try {
                OutputStream out = socket.getOutputStream();
                sendResponse(out, 500, "Internal Server Error", "text/html", "<h1>500 Error</h1>".getBytes());
            } catch (IOException ex) {
                // Ignore
            }
        } finally {
            try { socket.close(); } catch (IOException e) { }
        }
    }

    private void sendResponse(OutputStream out, int statusCode, String statusText, String contentType, byte[] content) throws IOException {
        PrintWriter writer = new PrintWriter(out);
        writer.println("HTTP/1.1 " + statusCode + " " + statusText);
        writer.println("Server: CustomJavaServer/1.0");
        writer.println("Content-Type: " + contentType);
        writer.println("Content-Length: " + content.length);
        writer.println("Connection: close");
        writer.println();
        writer.flush();
        
        out.write(content);
        out.flush();
    }
}

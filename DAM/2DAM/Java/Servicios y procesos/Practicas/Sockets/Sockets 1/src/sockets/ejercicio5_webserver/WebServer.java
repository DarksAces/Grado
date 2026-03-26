package sockets.ejercicio5_webserver;

import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.util.concurrent.*;
import java.security.KeyStore;

public class WebServer {
    private static Config config = new Config();
    // Using a Thread Pool to cap the maximum concurrent threads
    private static ExecutorService threadPool = Executors.newFixedThreadPool(150);

    public static void main(String[] args) {
        System.out.println("[DGB] === Java Socket Web Server (Apache Clone) ===");
        
        try {
            // Load configuration
            config.load("conf/httpd.conf");
            System.out.println("[DGB] Config loaded from conf/httpd.conf");
            
            // Setup Logger
            Logger.configure(config.customLog, config.errorLog);
            System.out.println("[DGB] Logs configured.");

            // Start listeners based on configured Listen ports
            for (int port : config.listenPorts) {
                if (port == 443) {
                    new Thread(() -> startSSLServer(port)).start();
                } else {
                    new Thread(() -> startPlainServer(port)).start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logError("Startup failed: " + e.getMessage());
        }
    }

    private static void startPlainServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[DGB] HTTP Server listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.submit(new HttpHandler(clientSocket, config));
            }
        } catch (IOException e) {
            Logger.logError("Failed to start HTTP server on port " + port + ": " + e.getMessage());
        }
    }

    private static void startSSLServer(int port) {
        try {
            // Load KeyStore with self-signed certificate
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("conf/keystore.jks"), "password".toCharArray());

            // Initialize KeyManagerFactory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, "password".toCharArray());

            // Initialize SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, null);

            SSLServerSocketFactory sslFactory = sslContext.getServerSocketFactory();
            try (SSLServerSocket sslServerSocket = (SSLServerSocket) sslFactory.createServerSocket(port)) {
                System.out.println("[DGB] HTTPS Server listening on port " + port);
                while (true) {
                    Socket clientSocket = sslServerSocket.accept();
                    threadPool.submit(new HttpHandler(clientSocket, config));
                }
            }
        } catch (Exception e) {
            Logger.logError("Failed to start HTTPS server on port " + port + ": " + e.getMessage());
        }
    }
}

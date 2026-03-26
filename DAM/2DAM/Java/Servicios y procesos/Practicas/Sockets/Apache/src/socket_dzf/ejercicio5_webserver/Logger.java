package socket_dzf.ejercicio5_webserver;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static String accessLogPath = "logs/access.log";
    private static String errorLogPath = "logs/error.log";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void configure(String accessPath, String errorPath) {
        if (accessPath != null && !accessPath.isEmpty()) accessLogPath = accessPath;
        if (errorPath != null && !errorPath.isEmpty()) errorLogPath = errorPath;
        
        // Ensure logs directory exists
        try {
            Files.createDirectories(Paths.get("logs"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void logAccess(String clientIp, String requestLine, int statusCode) {
        String msg = String.format("[%s] %s \"%s\" %d%n", 
                LocalDateTime.now().format(formatter), clientIp, requestLine, statusCode);
        writeToFile(accessLogPath, msg);
    }

    public static synchronized void logError(String msg) {
        String errorMsg = String.format("[%s] [error] %s%n", 
                LocalDateTime.now().format(formatter), msg);
        writeToFile(errorLogPath, errorMsg);
        System.err.print(errorMsg);
    }

    private static void writeToFile(String path, String msg) {
        try (FileWriter fw = new FileWriter(path, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(msg);
        } catch (IOException e) {
            System.err.println("Could not write to log file " + path + ": " + e.getMessage());
        }
    }
}

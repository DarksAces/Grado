package sockets.ejercicio5_webserver;

import java.io.*;
import java.util.*;

public class Config {
    public String serverRoot = "";
    public String defaultDocumentRoot = "";
    public List<Integer> listenPorts = new ArrayList<>();
    public String errorLog = "";
    public String customLog = "";
    public List<VirtualHost> virtualHosts = new ArrayList<>();

    public void load(String configFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFilePath))) {
            String line;
            String currentVHostName = null;
            String currentVHostRoot = null;
            boolean insideVHost = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                if (line.startsWith("<VirtualHost")) {
                    insideVHost = true;
                    currentVHostName = null;
                    currentVHostRoot = null;
                    continue;
                }
                
                if (line.equals("</VirtualHost>")) {
                    if (currentVHostName != null && currentVHostRoot != null) {
                        virtualHosts.add(new VirtualHost(currentVHostName, currentVHostRoot));
                    }
                    insideVHost = false;
                    continue;
                }

                String[] parts = line.split("\\s+", 2);
                if (parts.length < 2) continue;

                String key = parts[0];
                String value = parts[1].replace("\"", ""); // remove quotes if any

                if (insideVHost) {
                    if (key.equalsIgnoreCase("ServerName")) currentVHostName = value;
                    else if (key.equalsIgnoreCase("DocumentRoot")) currentVHostRoot = value;
                } else {
                    switch (key.toLowerCase()) {
                        case "serverroot": serverRoot = value; break;
                        case "listen": listenPorts.add(Integer.parseInt(value)); break;
                        case "documentroot": defaultDocumentRoot = value; break;
                        case "errorlog": errorLog = value; break;
                        case "customlog": customLog = value; break;
                    }
                }
            }
        }
    }
}

package sockets.ejercicio5_webserver;

public class VirtualHost {
    private String serverName;
    private String documentRoot;

    public VirtualHost(String serverName, String documentRoot) {
        this.serverName = serverName;
        this.documentRoot = documentRoot;
    }

    public String getServerName() {
        return serverName;
    }

    public String getDocumentRoot() {
        return documentRoot;
    }
}

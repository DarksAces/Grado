package sockets.ejercicio4_simulacion;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NginxSimulator {
    private static final int PORT = 8080;
    private static int activeConnections = 0;

    public static void main(String[] args) {
        System.out.println("[DZF] === Nginx Simulator (Event-Driven NIO) ===");
        System.out.println("[DZF] Listening on port " + PORT + " using non-blocking I/O...");

        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(PORT));
            serverSocket.configureBlocking(false);

            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select(); 
                
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();

                    if (key.isAcceptable()) {
                        SocketChannel client = serverSocket.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        
                        activeConnections++;
                        if (activeConnections % 100 == 0) {
                            System.out.println("[DZF] Active Connections: " + activeConnections + " (Running on 1 Thread)");
                        }
                    }

                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(256);
                        
                        try {
                            int bytesRead = client.read(buffer);
                            if (bytesRead == -1) {
                                client.close();
                                key.cancel();
                                activeConnections--;
                            }
                        } catch (IOException e) {
                            client.close();
                            key.cancel();
                            activeConnections--;
                        }
                    }
                    iter.remove(); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

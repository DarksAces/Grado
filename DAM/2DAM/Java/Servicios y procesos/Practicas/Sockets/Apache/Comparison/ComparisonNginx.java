import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

/**
 * Comparison Nginx Simulation
 * Uses Java NIO (Selector) to handle many connections in a single thread (Event-driven).
 */
public class ComparisonNginx {
    private static final int PORT = 7000;

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(PORT));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Nginx-like Server (Event-driven) started on port " + PORT);

        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    register(selector, serverChannel);
                }

                if (key.isWritable()) {
                    answer(key);
                }

                iter.remove();
            }
        }
    }

    private static void register(Selector selector, ServerSocketChannel serverChannel) throws IOException {
        SocketChannel client = serverChannel.accept();
        client.configureBlocking(false);
        // In a real Nginx, we'd wait for the request, but here we just mark it ready to write the response
        client.register(selector, SelectionKey.OP_WRITE);
        System.out.println("New connection accepted...");
    }

    private static void answer(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        String response = "HTTP/1.1 200 OK (Processed by Nginx Event Loop)\n";
        ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
        client.write(buffer);
        client.close();
        System.out.println("Response sent.");
    }
}

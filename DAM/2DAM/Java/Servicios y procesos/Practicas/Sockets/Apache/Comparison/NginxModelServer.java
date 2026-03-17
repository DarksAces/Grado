import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

/**
 * Nginx-style Server Mock (Event-driven / Non-blocking)
 * Uses Java NIO Selector to handle multiple connections in a single thread.
 */
public class NginxModelServer {
    public static void main(String[] args) {
        int port = 9002;
        try (Selector selector = Selector.open();
             ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            
            serverChannel.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Nginx-style Server (Event-driven/NIO) on port " + port);

            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();

                    if (key.isAcceptable()) {
                        register(selector, serverChannel);
                    }
                    if (key.isReadable()) {
                        answer(key);
                    }
                    iter.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void register(Selector selector, ServerSocketChannel serverChannel) throws IOException {
        SocketChannel client = serverChannel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("Accepted new connection in main event loop thread");
    }

    private static void answer(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(256);
        client.read(buffer);
        
        System.out.println("Handling request asynchronously in event loop");
        
        String response = "Response from Nginx Event-driven model\n";
        client.write(ByteBuffer.wrap(response.getBytes()));
        client.close();
    }
}

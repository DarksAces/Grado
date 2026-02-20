package hilos2;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockBufferDemo {
    // Buffer acotado
    static class BoundedBuffer<T> {
        private final Queue<T> queue = new ArrayDeque<>();
        private final int capacity;
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition notFull = lock.newCondition(); // buffer lleno -> esperar
        private final Condition notEmpty = lock.newCondition(); // buffer vacío -> esperar

        BoundedBuffer(int capacity) {
            this.capacity = capacity;
        }

        public void put(T item) throws InterruptedException {
            lock.lock();
            try {
                System.out.println("DGB [PRODUCER] intenta poner item=" + item + " (buffer=" + queue.size() + ")");
                while (queue.size() == capacity) {
                    System.out.println("DGB [WAIT] Producer BLOQUEADO -> buffer lleno");
                    notFull.await();
                }
                queue.add(item);
                System.out.println("DGB [PUT] Producer puso item=" + item + " (buffer=" + queue.size() + ")");
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public T take() throws InterruptedException {
            lock.lock();
            try {
                System.out.println("DGB [CONSUMER] intenta quitar item (buffer=" + queue.size() + ")");
                while (queue.isEmpty()) {
                    System.out.println("DGB [WAIT] Consumer BLOQUEADO -> buffer vacío");
                    notEmpty.await();
                }
                T item = queue.remove();
                System.out.println("DGB [TAKE] Consumer quitó item=" + item + " (buffer=" + queue.size() + ")");
                notFull.signal();
                return item;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        // TEST 1: Capacidad del Buffer (Cambia el 5 por 1 o 10)
        BoundedBuffer<Integer> buffer = new BoundedBuffer<>(5);
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                try {
                    buffer.put(i);
                    // Los prints internos ya tienen DGB
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Producer DGB");
        Thread consumer = new Thread(() -> {
            // TEST 3: Si usas 2 productores, aumenta esto a 40 para no bloquearte
            int totalItems = 20;
            // int totalItems = 40; // Descomenta si usas TEST 3

            for (int i = 1; i <= totalItems; i++) {
                try {
                    int v = buffer.take();
                    // Los prints internos ya tienen DGB

                    // TEST 2: Velocidad del Consumidor (Cambia los 50ms)
                    Thread.sleep(50);
                    // Thread.sleep(500); // Consumidor lento -> se llena el buffer
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Consumer DGB");
        producer.start();

        // TEST 3: Múltiples Productores (Descomenta estas líneas)
        // Thread producer2 = new Thread(producer, "Producer 2 DGB");
        // producer2.start();

        consumer.start();
    }
}

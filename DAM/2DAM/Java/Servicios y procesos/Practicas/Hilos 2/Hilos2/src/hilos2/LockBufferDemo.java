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
                while (queue.size() == capacity) {
                    // Espera hasta que haya hueco
                    notFull.await();
                }
                queue.add(item);
                // Ahora hay al menos 1 elemento
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public T take() throws InterruptedException {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    // Espera hasta que haya algo que consumir
                    notEmpty.await();
                }
                T item = queue.remove();
                // Ahora hay al menos 1 hueco
                notFull.signal();
                return item;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        BoundedBuffer<Integer> buffer = new BoundedBuffer<>(5);
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                try {
                    buffer.put(i);
                    System.out.println("[DGB] " + Thread.currentThread().getName() + " produce: " + i);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Producer");
        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                try {
                    int v = buffer.take();
                    System.out.println("[DGB] " + Thread.currentThread().getName() + " consume: " + v);
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Consumer");
        producer.start();
        consumer.start();
    }
}

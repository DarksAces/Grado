package hilos2;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockBufferDemoConMuchosPrintln {

    static class BoundedBuffer<T> {

        private final Queue<T> queue = new ArrayDeque<>();
        private final int capacity;

        private final ReentrantLock lock = new ReentrantLock();
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();

        BoundedBuffer(int capacity) {
            this.capacity = capacity;
        }

        public void put(T item) throws InterruptedException {
            System.out.println("[DGB] [PRODUCER] wants to produce item=" + item);

            lock.lock();
            try {
                System.out.println("[DGB] [LOCK] Producer enters lock for item=" + item
                        + " (buffer=" + queue.size() + ")");

                while (queue.size() == capacity) {
                    System.out.println("[DGB] [WAIT] Producer BLOCKED for item=" + item
                            + " -> buffer FULL");
                    notFull.await();
                    System.out.println("[DGB] [WAKE] Producer wakes up for item=" + item
                            + " (buffer=" + queue.size() + ")");
                }

                queue.add(item);
                System.out.println("[DGB] [PUT ] Producer produces item=" + item
                        + " (buffer=" + queue.size() + ")");

                System.out.println("[DGB] [SIGNAL] notEmpty after producing item=" + item);
                notEmpty.signal();

            } finally {
                System.out.println("[DGB] [UNLOCK] Producer releases lock after item=" + item);
                lock.unlock();
            }
        }

        public T take() throws InterruptedException {
            T item = null; // lo inicializamos fuera

            System.out.println("[DGB] [CONSUMER] wants to consume next item");

            lock.lock();
            try {
                System.out.println("[DGB] [LOCK] Consumer enters lock (buffer=" + queue.size() + ")");

                while (queue.isEmpty()) {
                    System.out.println("[DGB] [WAIT] Consumer BLOCKED -> buffer EMPTY");
                    notEmpty.await();
                    System.out.println("[DGB] [WAKE] Consumer wakes up (buffer=" + queue.size() + ")");
                }

                item = queue.remove();
                System.out.println("[DGB] [TAKE] Consumer consumes item=" + item
                        + " (buffer=" + queue.size() + ")");

                System.out.println("[DGB] [SIGNAL] notFull after consuming item=" + item);
                notFull.signal();

            } finally {
                System.out.println("[DGB] [UNLOCK] Consumer releases lock after item=" + item);
                lock.unlock();
            }

            return item;
        }
    }

    public static void main(String[] args) {

        // CAMBIA ESTE VALOR: 2 o 6
        BoundedBuffer<Integer> buffer = new BoundedBuffer<>(2);

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    buffer.put(i);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    buffer.take();
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
